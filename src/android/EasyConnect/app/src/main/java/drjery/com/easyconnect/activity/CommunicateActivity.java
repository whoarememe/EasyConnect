package drjery.com.easyconnect.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import drjery.com.easyconnect.R;
import drjery.com.easyconnect.adapter.MessageListAdapter;
import drjery.com.easyconnect.fragment.MessageFragment;
import drjery.com.easyconnect.pojo.Device;
import drjery.com.easyconnect.pojo.Message;
import drjery.com.easyconnect.util.ConstUtil;
import drjery.com.easyconnect.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

public class CommunicateActivity extends AppCompatActivity implements View.OnClickListener{

    private String devName;

    private Button send;

    private Button getOrder;

    private EditText inputLine;

    private RecyclerView msgRecyclerView;

    private MessageListAdapter adapter;

    private List<Message> messages;

    private int userId;

    private Device device;

    private IntentFilter intentFilter;

    private LocalReceiver localReceiver;

    private LocalBroadcastManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        initMessages();
        init();
        addToolBar();

    }

    private void addToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.message_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(devName);
        }
    }

    private void init(){
        manager = LocalBroadcastManager.getInstance(getContext());
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConstUtil.MESSAGE_RECEIVE);
        localReceiver = new LocalReceiver();
        manager.registerReceiver(localReceiver,intentFilter);
        devName = "["+device.getManufacturer()+"]"+device.getType()+" "+device.getModel();
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);
        getOrder = (Button) findViewById(R.id.get_order_button);
        getOrder.setOnClickListener(this);
        inputLine = (EditText) findViewById(R.id.input_text);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycle_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(manager);
        adapter = new MessageListAdapter(messages);
        msgRecyclerView.setAdapter(adapter);
        msgRecyclerView.scrollToPosition(messages.size()-1);
    }


    private void initMessages() {
        device = (Device) getIntent().getSerializableExtra("device");
        SharedPreferences pref = this.getSharedPreferences("data",MODE_PRIVATE);
        userId = pref.getInt("userId",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("communitDevId",device.getDeviceId());
        editor.commit();
        messages = new ArrayList<Message>();
        Log.e("newSize",String.valueOf(device.getDeviceId()));
        Log.e("newSize",String.valueOf(userId));
        List<Message> newest = DataSupport.where("devId = ?",String.valueOf(device.getDeviceId())).order("time").find(Message.class);

        messages.addAll(newest);
        Log.e("newSize", String.valueOf(newest.size()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send:
                String content = inputLine.getText().toString();
                if(!content.equals("")){

                    final long time = System.currentTimeMillis();

                    final Message message = new Message(1,userId,device.getDeviceId(),1,time,content,0,0);

                    messages.add(message);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InetAddress addr = InetAddress.getByName(ConstUtil.addr);
                                int port = ConstUtil.port;
                                JSONObject packet = new JSONObject();
                                packet.put("code",2);
                                packet.put("direction",message.getSender());
                                packet.put("userId",message.getUserId());
                                packet.put("deviceId",message.getDevId());
                                packet.put("msgType",message.getMessageType());
                                packet.put("msg",message.getMessage());
                                packet.put("level",message.getLevel());
                                packet.put("hasRead",message.getHasRead());
                                packet.put("time",time);
                                byte sendBuf[] = packet.toString().getBytes();
                                DatagramPacket sendPacket = new DatagramPacket(sendBuf,sendBuf.length,addr,port);
                                ConstUtil.mySocket.send(sendPacket);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    message.save();

                    adapter.notifyItemInserted(messages.size()-1);

                    msgRecyclerView.scrollToPosition(messages.size()-1);

                    inputLine.setText("");

                }
                break;


            case R.id.get_order_button:

                List<String> data = new ArrayList<String>();

                data.add("functionId");
                data.add(String.valueOf(device.getFunctionId()));
                Log.e("functionId",String.valueOf(device.getFunctionId()));

                String url = ConstUtil.url+"/function/getDevFunc";

                HttpUtil.sendOkHttpRequest(url, data, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("getOrder","fail");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        try {
                            JSONObject resultObject  = new JSONObject(responseText);
                            String status = resultObject.getString("success");
                            Log.e("json",resultObject.toString());
                            if(status.equals("true"))
                            {

                                String fucntions = resultObject.getString("data");
                                Message message = new Message(2,0,0,1,0,fucntions,0,0);
                                messages.add(message);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyItemInserted(messages.size()-1);
                                        msgRecyclerView.scrollToPosition(messages.size()-1);
                                    }
                                });

                            }
                            else
                            {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Message message = (Message) intent.getSerializableExtra("NewMessage");
            messages.add(message);
            adapter.notifyItemInserted(messages.size()-1);
            msgRecyclerView.scrollToPosition(messages.size()-1);
            adapter.notifyDataSetChanged();
        }
    }
}
