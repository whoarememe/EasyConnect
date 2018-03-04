package drjery.com.easyconnect.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import drjery.com.easyconnect.R;
import drjery.com.easyconnect.pojo.Device;

public class DeviceActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView manufacturer;

    private TextView type;

    private TextView model;

    private TextView devId;

    private TextView description;

    private Button controll;

    private Button share;

    private LinearLayout changePasswd;

    private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Log.e("Device","activity");
        init(); //初始化布局
        initData(); //初始化数据
        addToolBar(); //添加顶部标题栏
    }

    /*初始化界面布局*/
    private void init()
    {
        manufacturer = (TextView) findViewById(R.id.dev_act_company_name);
        type = (TextView) findViewById(R.id.dev_act_type_name);
        model = (TextView) findViewById(R.id.dev_act_model_name);
        devId = (TextView) findViewById(R.id.dev_act_dev_id);
        description = (TextView) findViewById(R.id.dev_act_description);
        controll = (Button) findViewById(R.id.dev_act_control_button);
        share = (Button) findViewById(R.id.dev_act_share_button);
        changePasswd = (LinearLayout) findViewById(R.id.dev_act_change_passwd_layout);
        controll.setOnClickListener(this);
        share.setOnClickListener(this);
        changePasswd.setOnClickListener(this);

    }


    /*添加标题栏*/
    private void addToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.dev_act_toobar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }


    //导航栏点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    private void initData()
    {
        Intent itent = getIntent(); //获取Intent中的数据
        device = (Device) itent.getSerializableExtra("device"); //名字是dvice
        manufacturer.setText(device.getManufacturer());
        type.setText(device.getType());
        model.setText(device.getModel());
        devId.setText(String.valueOf(device.getDeviceId()));
        description.setText(device.getDescription());
        CircleImageView devImg = (CircleImageView) findViewById(R.id.dev_head_icon);
        if(device.getType().equals("Light"))
        {
            devImg.setImageResource(R.drawable.ic_bulb_png);
        }
        else if(device.getType().equals("Monitor"))
        {
            devImg.setImageResource(R.drawable.ic_camera_png);
        }
    }

    @Override
    public void onClick(View v) { //设置点击事件
        switch (v.getId())
        {
            case R.id.dev_act_share_button: //点击share进入分享界面
                Intent intent_share = new Intent(DeviceActivity.this,ShareDeviceActivity.class);
                intent_share.putExtra("deviceId",device.getDeviceId());
                startActivity(intent_share);
                break;
            case R.id.dev_act_control_button: //点击control进入控制界面
                Intent intent = new Intent(DeviceActivity.this,CommunicateActivity.class);
                intent.putExtra("device",device);
                startActivity(intent);
                break;
            case R.id.dev_act_change_passwd_layout: //点击修改密码进入修改密码界面
                Intent intent_change = new Intent(DeviceActivity.this,ChangeDevicePasswdActivity.class);
                intent_change.putExtra("deviceId",device.getDeviceId());
                startActivity(intent_change);
                break;
        }
    }
}
