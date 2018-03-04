package drjery.com.easyconnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import drjery.com.easyconnect.R;
import drjery.com.easyconnect.pojo.Device;
import drjery.com.easyconnect.pojo.Message;
import drjery.com.easyconnect.pojo.MessageOfDevice;

/**
 * Created by DRJ on 2017/7/3.
 */

public class MessageAdapter extends ArrayAdapter {

    private int messageId;


    public MessageAdapter(Context context, int resource, List<MessageOfDevice> objects) {
        super(context, resource, objects);
        messageId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageOfDevice md = (MessageOfDevice) getItem(position);
        Device device = md.getDevice();
        Message message = md.getMessages();
        View view = LayoutInflater.from(getContext()).inflate(messageId,parent,false);
        CircleImageView devImg = (CircleImageView) view.findViewById(R.id.message_dev_image);
        TextView comName = (TextView) view.findViewById(R.id.message_company);
        TextView devName = (TextView) view.findViewById(R.id.message_dev);
        TextView time = (TextView) view.findViewById(R.id.message_time);
        ImageView point = (ImageView) view.findViewById(R.id.point_img);
        String type = device.getType();
        if(type.equals("Light"))
        {
            devImg.setImageResource(R.drawable.ic_bulb_png);
        }else if(type.equals("Monitor"))
        {
            devImg.setImageResource(R.drawable.ic_camera_png);
        }

        comName.setText(device.getManufacturer());
        devName.setText(device.getType()+" "+device.getModel());

        Timestamp timestamp = new Timestamp(message.getTime());
        time.setText(timestamp.toString().substring(0,16));

        if(message.getLevel()==3)
        {
            point.setImageResource(R.drawable.point);
        }
        else if(message.getLevel()==2)
        {
            point.setImageResource(R.drawable.point_blue);
        }else
        {
            point.setVisibility(View.GONE);
        }

        return view;
    }
}
