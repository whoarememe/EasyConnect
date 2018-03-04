package drjery.com.easyconnect.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.os.Handler;


import drjery.com.easyconnect.R;

import drjery.com.easyconnect.activity.DeviceActivity;
import drjery.com.easyconnect.adapter.DeviceAdapter;
import drjery.com.easyconnect.pojo.Device;


/**
 * Created by DRJ on 2017/7/3.
 */

public class DeviceFragment extends Fragment {

    private ListView deviceListView;

    private List<Device> deviceList;

    private SwipeRefreshLayout swipeRefresh;

    private DeviceAdapter adapter;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0x101:
                    if (swipeRefresh.isRefreshing())
                    {
                        adapter.notifyDataSetChanged();
                        Log.e("refreshed","refreshed");
                        swipeRefresh.setRefreshing(false);//设置不刷新
                    }
                    break;
            }
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dev, container, false);
        init(view);

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        if(hidden)
        {
            List<Device> devices= DataSupport.findAll(Device.class);
            deviceList.clear();
            deviceList.addAll(devices);
            handler.sendEmptyMessage(0x101);
        }
        super.onHiddenChanged(hidden);
    }

    private void init(View view)
    {
        deviceListView = (ListView) view.findViewById(R.id.device_list);

        deviceList = new ArrayList<Device>();
        deviceList.addAll(DataSupport.findAll(Device.class));

        adapter = new DeviceAdapter(getActivity(),R.layout.dev_item,deviceList);
        deviceListView.setAdapter(adapter);

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Device device = deviceList.get(position);
                Intent intent = new Intent();
                intent.setClass(getActivity(), DeviceActivity.class);
                intent.putExtra("device",device);
                startActivity(intent);
            }
        });

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.dev_frag_swipe);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataThread().start();
            }
        });
    }

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            List<Device> devices= DataSupport.findAll(Device.class);
            deviceList.clear();
            deviceList.addAll(devices);
            Log.e("size", String.valueOf(deviceList.size()));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }

}
