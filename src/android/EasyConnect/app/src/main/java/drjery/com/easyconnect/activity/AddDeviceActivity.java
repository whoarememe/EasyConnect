package drjery.com.easyconnect.activity;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import drjery.com.easyconnect.R;
import drjery.com.easyconnect.pojo.Device;
import drjery.com.easyconnect.pojo.User;
import drjery.com.easyconnect.util.ConstUtil;
import drjery.com.easyconnect.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddDeviceActivity extends AppCompatActivity {

    private EditText devId;

    private EditText devPasswd;

    private Button addDevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        init();
        addToolBar();
    }

    private void init(){ //初始化界面
        devId = (EditText) findViewById(R.id.add_dev_devId_edit);
        devPasswd = (EditText) findViewById(R.id.add_dev_devPasswd_edit);
        addDevButton = (Button) findViewById(R.id.add_dev_button);
        addDevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDevice();
            }
        });
    }

    private void addToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_dev_act_toobar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("添加设备");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish(); //点击返回按钮关闭界面
                break;
        }
        return true;
    }

    
    private void addDevice(){
        SharedPreferences pref = this.getSharedPreferences("data",MODE_PRIVATE);
        String url = ConstUtil.url+"/userDevice/addDevice";  //确定url地址
        String userId = String.valueOf(pref.getInt("userId",0));   //获取用户ID
        String id = devId.getText().toString();
        String password = devPasswd.getText().toString();
        List<String> data = new ArrayList<String>();
        data.add("userId");
        data.add(userId);
        data.add("deviceId");
        data.add(id);
        data.add("devPasswd");
        data.add(password);
        HttpUtil.sendOkHttpRequest(url, data, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.e("addDev",responseText);
                try {
                    final JSONObject resultObject  = new JSONObject(responseText);
                    String status = resultObject.getString("success");
                    if(status.equals("true"))
                    {
                        JSONObject deviceObject = resultObject.getJSONObject("data");
                        Gson gson = new Gson();
                        Device device = gson.fromJson(deviceObject.toString(),Device.class);
                        device.save();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddDeviceActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String message = " ";
                                try {
                                     message = resultObject.getString("message");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(AddDeviceActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
