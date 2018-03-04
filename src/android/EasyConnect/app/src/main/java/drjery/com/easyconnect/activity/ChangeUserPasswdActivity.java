package drjery.com.easyconnect.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
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
import drjery.com.easyconnect.util.MD5;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangeUserPasswdActivity extends AppCompatActivity {

    private ActionBar actionBar;

    private EditText oldPasswdEdit;

    private EditText newPasswdEdit;

    private EditText againPasswdEdit;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_passwd);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.chang_paswd_toobar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("修改密码");
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
        oldPasswdEdit = (EditText) findViewById(R.id.change_old_passwd_edit);
        newPasswdEdit = (EditText) findViewById(R.id.change_new_passwd_edit);
        againPasswdEdit = (EditText) findViewById(R.id.change_again_passwd_edit);
        button  = (Button) findViewById(R.id.change_pass_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = oldPasswdEdit.getText().toString();
                String newPass = newPasswdEdit.getText().toString();
                String againPass = againPasswdEdit.getText().toString();
                if(oldPass.equals("")||newPass.equals("")||againPass.equals(""))
                {
                    Toast.makeText(ChangeUserPasswdActivity.this,"请输入全部信息",Toast.LENGTH_SHORT).show();
                }else if(!newPass.equals(againPass))
                {
                    Toast.makeText(ChangeUserPasswdActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }else{
                    String url = ConstUtil.url+"/user/modifyPassword";
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    List<String> data = new ArrayList<String>();
                    data.add("userId");
                    data.add(String.valueOf(pref.getInt("userId",0)));
                    data.add("password");
                    data.add(MD5.getMD5Code(oldPass));
                    data.add("newPassword");
                    data.add(MD5.getMD5Code(newPass));
                    HttpUtil.sendOkHttpRequest(url, data, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().string();
                            JSONObject resultObject  = null;
                            try {
                                resultObject = new JSONObject(responseText);
                                String status = resultObject.getString("success");
                                if(status.equals("true"))
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChangeUserPasswdActivity.this,"修改成功",Toast.LENGTH_LONG).show();

                                        }
                                    });
                                    finish();
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChangeUserPasswdActivity.this,"密码错误",Toast.LENGTH_LONG).show();
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
