package drjery.com.easyconnect.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import drjery.com.easyconnect.activity.CommunicateActivity;
import drjery.com.easyconnect.adapter.MessageAdapter;
import drjery.com.easyconnect.pojo.Device;
import drjery.com.easyconnect.pojo.Message;
import drjery.com.easyconnect.R;
import drjery.com.easyconnect.pojo.MessageOfDevice;
import drjery.com.easyconnect.util.ConstUtil;

/**
 * Created by DRJ on 2017/7/2.
 */

public class MessageFragment extends Fragment {

    private ListView messageListView;

    private List<MessageOfDevice> messageOfDeviceList;

    private List<Device> devices;

    private IntentFilter intentFilter;

    private LocalReceiver localReceiver;

    private LocalBroadcastManager manager;

    private MessageAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0x101:
                    if (swipeRefresh.isRefreshing())
                    {
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);//设置不刷新
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message , container, false);
        init(view);
        Log.e("messageFrag","init");
        manager = LocalBroadcastManager.getInstance(getContext());
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConstUtil.MESSAGE_RECEIVE);
        localReceiver = new LocalReceiver();
        manager.registerReceiver(localReceiver,intentFilter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(localReceiver);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        if(!hidden)
        {
            messageOfDeviceList.clear();
            getMessageOfDeviceList();
            handler.sendEmptyMessage(0x101);
            Log.e("showMessageFrag","1");
        }
        super.onHiddenChanged(hidden);
    }

    private void init(View view)
    {
        messageListView = (ListView) view.findViewById(R.id.message_list);
        messageOfDeviceList = new ArrayList<MessageOfDevice>();
        //devices = new ArrayList<Device>();
        //devices.add(new Device(19786801,1,"j","de","px",1,"12"));
        getMessageOfDeviceList();

        adapter = new MessageAdapter(getActivity(),R.layout.message_item,messageOfDeviceList);
        messageListView.setAdapter(adapter);

        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageOfDevice md = messageOfDeviceList.get(position);
                md.getMessages().setLevel(0);
                Message a = new Message();
                a.setToDefault("level");
                a.setHasRead(1);
                a.updateAll("devId = ? and time = ?",
                        String.valueOf(md.getMessages().getDevId()),String.valueOf(md.getMessages().getTime()));
                messageOfDeviceList.remove(position);
                messageOfDeviceList.add(position,md);
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(getActivity(),CommunicateActivity.class);
                intent.putExtra("device",md.getDevice());
                startActivity(intent);
            }
        });

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.msg_frag_swipe);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataThread().start();
            }
        });
    }


    private void getMessageOfDeviceList()
    {
        devices = DataSupport.findAll(Device.class);
        for(Device device : devices)
        {
            MessageOfDevice md = new MessageOfDevice();
            List<Message> myMessage= DataSupport.where("devId = ?",String.valueOf(device.getDeviceId())).find(Message.class);
            if(myMessage.size()==0)
            {
                if(device.getDeviceId()==123123)
                {
                    Log.e("123123","noMsg");
                }
            }
            else
            {
                md.setDevice(device);
                md.setMessages(myMessage.get(myMessage.size()-1));
                messageOfDeviceList.add(md);
                Log.e("added","msgFrag");
            }
        }
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            messageOfDeviceList.clear();
            getMessageOfDeviceList();
            adapter.notifyDataSetChanged();
        }
    }


    private class LoadDataThread extends Thread{

        @Override
        public void run() {
            messageOfDeviceList.clear();
            getMessageOfDeviceList();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }
}
