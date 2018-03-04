package drjery.com.easyconnect.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import drjery.com.easyconnect.R;

import drjery.com.easyconnect.pojo.User;
import drjery.com.easyconnect.util.HttpUtil;

import drjery.com.easyconnect.util.ConstUtil;
import drjery.com.easyconnect.util.MD5;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Button logIn;

    private EditText phoneEdit;

    private EditText passwdEdit;

    private TextView regist;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isLogin();
        logIn = (Button) findViewById(R.id.login_button);
        phoneEdit = (EditText) findViewById(R.id.account_edit);
        passwdEdit = (EditText) findViewById(R.id.password_edit);
        regist = (TextView) findViewById(R.id.regist_textView);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(phoneEdit.getText()) ||TextUtils.isEmpty(passwdEdit.getText()))
                {
                    Toast.makeText(LoginActivity.this,"请输入用户名和密码",Toast.LENGTH_SHORT).show();
                }else{
                    logIn();
                }

            }
        });
    }

    private void isLogin()
    {
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        int id = pref.getInt("userId",-1);
        if(id!=-1)
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        Log.e("Login:",String.valueOf(id));
    }


    protected void logIn(){
        String phone = phoneEdit.getText().toString();
        final String password = passwdEdit.getText().toString();
        List<String> data = new ArrayList<String>();
        data.add("phone");
        data.add(phone);
        data.add("password");
        data.add(MD5.getMD5Code(password));
        String url = ConstUtil.url+"/user/userLogin";
        HttpUtil.sendOkHttpRequest(url, data, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"服务器正忙，请重试",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                try {
                    JSONObject resultObject  = new JSONObject(responseText);
                    resultObject.keys();
                    String status = resultObject.getString("success");
                    if(status.equals("true"))
                    {
                        JSONObject userObject = resultObject.getJSONObject("data");

                        user = new Gson().fromJson(userObject.toString(),User.class);

                        Log.e("User", String.valueOf(user.getUserId()));

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();

                        editor.putInt("userId",user.getUserId());
                        editor.putString("userName",user.getName());
                        editor.putString("userPhone",user.getPhone());

                        Log.e("userName",user.getName());
                        editor.commit();

                        startActivity(intent);
                        finish();

                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
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
