package drjery.com.easyconnect.activity;

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

import drjery.com.easyconnect.R;
import drjery.com.easyconnect.util.ConstUtil;
import drjery.com.easyconnect.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShareDeviceActivity extends AppCompatActivity {

    private ActionBar actionBar; //顶部菜单栏

    private EditText userEdit;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_device);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.share_device_toobar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("共享设备");
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

    private void init()
    {
        userEdit = (EditText) findViewById(R.id.share_device_edit); //获取控件
        button = (Button) findViewById(R.id.share_device_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = userEdit.getText().toString();
                String url = ConstUtil.url+"/userDevice/shareDevice";
                if(userPhone.equals(""))
                {
                    Toast.makeText(ShareDeviceActivity.this,"请输入信息",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    List<String> data = new ArrayList<String>();
                    int deviceId = getIntent().getIntExtra("deviceId",0);
                    data.add("phone");
                    data.add(userPhone);
                    data.add("deviceId");
                    data.add(String.valueOf(deviceId));
                    HttpUtil.sendOkHttpRequest(url, data, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(ShareDeviceActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().string();
                            try {
                                final JSONObject resultObject = new JSONObject(responseText);
                                String status = resultObject.getString("success");
                                if(status.equals("true"))
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ShareDeviceActivity.this,"共享成功",Toast.LENGTH_LONG).show();

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
                                            Toast.makeText(ShareDeviceActivity.this,message,Toast.LENGTH_LONG).show();
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
