package drjery.com.easyconnect.activity;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import drjery.com.easyconnect.R;
import drjery.com.easyconnect.util.ConstUtil;
import drjery.com.easyconnect.util.HttpUtil;

public class ChangeDevicePasswdActivity extends AppCompatActivity {

    private ActionBar actionBar; //顶部菜单栏

    private EditText oldPasswdEdit; //旧密码输入

    private EditText newPasswdEdit; //新密码输入

    private EditText againPasswdEdit;//新密码再次输入

    private Button button;

    private int deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_device_passwd);
        init(); //初始化

        //下面为获取顶部菜单栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.chang_depaswd_toobar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("修改设备密码");//注意修改文字
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void init(){
        deviceId = getIntent().getIntExtra("deviceId",0);
        oldPasswdEdit = (EditText) findViewById(R.id.change_old_devicepasswd_edit); //获取控件
        newPasswdEdit = (EditText) findViewById(R.id.change_new_devicepasswd_edit);
        againPasswdEdit = (EditText) findViewById(R.id.change_again_devicepasswd_edit);
        button  = (Button) findViewById(R.id.change_devicepass_button);
        button.setOnClickListener(new View.OnClickListener() { //点击事件
            @Override
            public void onClick(View v) {
                //获取输入
                String oldPass = oldPasswdEdit.getText().toString();
                String newPass = newPasswdEdit.getText().toString();
                String againPass = againPasswdEdit.getText().toString();
                //判断是否为空
                if(oldPass.equals("")||newPass.equals("")||againPass.equals(""))
                {
                    Toast.makeText(ChangeDevicePasswdActivity.this,"请输入全部信息",Toast.LENGTH_SHORT).show();
                }else if(!newPass.equals(againPass)) //判断是否一致
                {
                    Toast.makeText(ChangeDevicePasswdActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }else{
                    String url = ConstUtil.url+"/userDevice/modifyDevicePassword"; //设置接口地址
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    List<String> data = new ArrayList<String>();  //设置参数集合，往参数集合中传递参数名称和参数
                    data.add("userId");
                    data.add(String.valueOf(pref.getInt("userId",0)));
                    data.add("deviceId");
                    data.add(String.valueOf(deviceId));
                    data.add("password");
                    data.add(oldPass);
                    data.add("newPassword");
                    data.add(newPass);
                    HttpUtil.sendOkHttpRequest(url, data, new Callback() { //调用工具类
                        @Override
                        public void onFailure(Call call, IOException e) { //访问失败

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException { //访问成功
                            final String responseText = response.body().string();
                            try {
                                final JSONObject resultObject  = new JSONObject(responseText);
                                String status = resultObject.getString("success");
                                if(status.equals("true"))
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChangeDevicePasswdActivity.this,"修改成功",Toast.LENGTH_LONG).show();

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
                                            Toast.makeText(ChangeDevicePasswdActivity.this,message,Toast.LENGTH_LONG).show();
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
        });
    }
}
