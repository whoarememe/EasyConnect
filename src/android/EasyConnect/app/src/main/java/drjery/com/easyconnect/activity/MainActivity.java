package drjery.com.easyconnect.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import drjery.com.easyconnect.R;
import drjery.com.easyconnect.Service.MessageService;
import drjery.com.easyconnect.fragment.DeviceFragment;
import drjery.com.easyconnect.fragment.MessageFragment;
import drjery.com.easyconnect.pojo.Device;
import drjery.com.easyconnect.pojo.Message;
import drjery.com.easyconnect.pojo.User;
import drjery.com.easyconnect.util.ConstUtil;
import drjery.com.easyconnect.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout myDrawerLayout;

    private MessageFragment messageFragment = null;

    private DeviceFragment deviceFragment = null;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private LinearLayout messageLay;

    private LinearLayout deviceLay;

    private LinearLayout friendsLay;

    private TextView userName;

    private TextView userPhone;

    private ActionBar actionBar;

    private SharedPreferences pref;

    private NavigationView nvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_share_white);
            actionBar.setTitle("消息");
        }
        getMessage();
        getDevicesList();
        messageFragment = new MessageFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragments,messageFragment);
        transaction.commit();
        Intent startIntent = new Intent(this,MessageService.class);
        startService(startIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                myDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.add_dev_menu:
                Intent intent = new Intent(MainActivity.this,AddDeviceActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction;
        switch (v.getId())
        {
            case R.id.ll_message:
                transaction = fragmentManager.beginTransaction();
                hideFragments(transaction);
                if(messageFragment==null)
                {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.fragments,messageFragment);
                    transaction.commit();
                }
                else
                {
                    transaction.show(messageFragment);
                    transaction.commit();
                }
                actionBar.setTitle("消息");
                break;
            case R.id.ll_dev:
                transaction = fragmentManager.beginTransaction();
                hideFragments(transaction);
                if(deviceFragment==null)
                {
                    deviceFragment = new DeviceFragment();
                    transaction.add(R.id.fragments,deviceFragment);
                    transaction.commit();
                }
                else
                {
                    transaction.show(deviceFragment);
                    transaction.commit();
                }
                actionBar.setTitle("设备");
                break;
            case R.id.ll_friend:
                Toast.makeText(MainActivity.this,"功能尚未开放，敬请期待。",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void init(){
        pref = this.getSharedPreferences("data",MODE_PRIVATE);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvView = (NavigationView) findViewById(R.id.nav_view);
        View headView = nvView.getHeaderView(0);

        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_change_passwd:
                        Intent intent = new Intent(MainActivity.this,ChangeUserPasswdActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_exit:
                        DataSupport.deleteAll(Message.class);
                        DataSupport.deleteAll(Device.class);
                        pref.edit().clear().commit();
                        Intent stopIntent = new Intent(MainActivity.this,MessageService.class);
                        stopService(stopIntent);
                        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                }
                return false;
            }
        });

        messageLay = (LinearLayout) findViewById(R.id.ll_message);
        deviceLay = (LinearLayout) findViewById(R.id.ll_dev);
        friendsLay = (LinearLayout) findViewById(R.id.ll_friend);
        messageLay.setOnClickListener(this);
        deviceLay.setOnClickListener(this);
        friendsLay.setOnClickListener(this);
        userName = (TextView) headView.findViewById(R.id.nav_user_name);
        userPhone = (TextView) headView.findViewById(R.id.nav_user_phone);
        userName.setText(pref.getString("userName"," "));
        userPhone.setText(pref.getString("userPhone"," "));
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (deviceFragment != null) {
            fragmentTransaction.hide(deviceFragment);
        }
        if (messageFragment != null) {
            fragmentTransaction.hide(messageFragment);
        }
    }

    private void getDevicesList(){
        String url = ConstUtil.url+"/userDevice/getUserDevice";
        String userId = String.valueOf(pref.getInt("userId",0));
        List<String> data = new ArrayList<String>();
        data.add("userId");
        data.add(userId);
        HttpUtil.sendOkHttpRequest(url, data, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("addDevice","fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                try {
                    JSONObject resultObject  = new JSONObject(responseText);
                    Log.e("result",resultObject.toString());
                    String status = resultObject.getString("success");
                    if(status.equals("true"))
                    {
                        JSONArray deviceObjects = resultObject.getJSONArray("data");
                        Gson gson = new Gson();
                        List<Device> devices = gson.fromJson(deviceObjects.toString(), new TypeToken<List<Device>>(){}.getType());
                        if(devices.size()!=0)
                        {
                            for(Device device : devices)
                            {
                                int size = DataSupport.where("deviceId = ?",String.valueOf(device.getDeviceId())).find(Device.class).size();
                                if(size == 0)
                                {
                                    device.save();
                                }
                            }
                        }
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getMessage(){
        String userId = String.valueOf(pref.getInt("userId",0));
        String url = ConstUtil.url+"/user/getUserUnreadMsg";
        List<String> data = new ArrayList<String>();
        data.add("userId");
        data.add(userId);
        HttpUtil.sendOkHttpRequest(url, data, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                try {
                    JSONObject resultObject = new JSONObject(responseText);
                    String status = resultObject.getString("success");
                    if(status.equals("true"))
                    {
                        JSONArray messageObjects = resultObject.getJSONArray("data");
                        Log.e("Mesasage",messageObjects.toString());
                        Gson gson = new Gson();
                        List<Message> messages = gson.fromJson(messageObjects.toString(), new TypeToken<List<Message>>(){}.getType());

                        if(messages.size()!=0)
                        {
                            for(Message message : messages)
                            {
                                Log.e("messgae",message.getMessage());
                                message.save();
                                Log.e("messagesaved","save");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
