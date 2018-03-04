package drjery.com.easyconnect.activity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private ActionBar actionBar;

    private EditText nameEdit;

    private EditText phoneEdit;

    private EditText mailEdit;

    private EditText passwdEdit;

    private EditText agPasswdEdit;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.regist_act_toobar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("注册");
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
        nameEdit = (EditText) findViewById(R.id.regist_userName_edit);
        phoneEdit = (EditText) findViewById(R.id.regist_userPhone_edit);
        mailEdit = (EditText) findViewById(R.id.regist_userMail_edit);
        passwdEdit = (EditText) findViewById(R.id.register_passwd_edit);
        agPasswdEdit = (EditText) findViewById(R.id.register_passwd_again_edit);
        button  = (Button) findViewById(R.id.regist_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                String mail = mailEdit.getText().toString();
                String password = passwdEdit.getText().toString();
                String password_again = agPasswdEdit.getText().toString();
                if(name.equals("")||phone.equals("")||mail.equals("")||password.equals("")||password_again.equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"请输入全部信息",Toast.LENGTH_SHORT).show();

                }
                else if(phone.length()!=11)
                {
                    Toast.makeText(RegisterActivity.this,"请输入正确的号码",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(password_again))
                {
                    Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();

                }
                else{
                    String url = ConstUtil.url+"/user/userRegister";
                    List<String> data = new ArrayList<String>();
                    data.add("name");
                    data.add(name);
                    data.add("phone");
                    data.add(phone);
                    data.add("mail");
                    data.add(mail);
                    data.add("password");
                    data.add(MD5.getMD5Code(password));
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
                                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                            nameEdit.setText("");
                                            phoneEdit.setText("");
                                            mailEdit.setText("");
                                            passwdEdit.setText("");
                                            agPasswdEdit.setText("");

                                        }
                                    });
                                    finish();
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_LONG).show();
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
