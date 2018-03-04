package drjery.com.easyconnect.adapter;

/**
 * Created by DRJ on 2017/7/4.
 */


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import drjery.com.easyconnect.R;
import drjery.com.easyconnect.pojo.Device;


public class DeviceAdapter extends ArrayAdapter{

    private int deviceId;



    public DeviceAdapter(Context context, int resource, List<Device> objects) {
        super(context, resource, objects);
        Log.e("hello", "devAda");
        deviceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.e("hello", "dev_getView");
        Device device = (Device) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(deviceId,parent,false);
        CircleImageView devImg = (CircleImageView) view.findViewById(R.id.dev_image);
        if(device.getType().equals("Light"))
        {
            devImg.setImageResource(R.drawable.ic_bulb_png);
        }else if (device.getType().equals("Monitor"))
        {
            devImg.setImageResource(R.drawable.ic_camera_png);
        }

        TextView comName = (TextView) view.findViewById(R.id.dev_company);
        TextView typeName = (TextView) view.findViewById(R.id.dev_type);
        TextView modelName = (TextView) view.findViewById(R.id.dev_name);
        comName.setText("[ "+device.getManufacturer()+" ]");


        typeName.setText(device.getType());
        modelName.setText(device.getModel());

        return view;
    }





}
