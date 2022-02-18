package com.example.smartcity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.smartcity.MainActivity;
import com.example.smartcity.R;
import com.example.smartcity.bean.LoginBean;
import com.example.smartcity.utils.GetRetrofit;
import com.example.smartcity.utils.MretrofitInterface;
import com.example.smartcity.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText edt_name;
    private EditText edt_psw;
    private Button btn_login;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        String spUserName = SPUtil.getString(this,"userName", "");
        String spPassword = SPUtil.getString(this,"password", "");//每次进入这个页面的时候读取已保存的账号密码
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_name.setText(spUserName);//如果有的话，显示之前登录用的账号密码
        edt_psw = (EditText) findViewById(R.id.edt_psw);
        edt_psw.setText(spPassword);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            try {
                login();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.btn_register) {
            Intent intent = new Intent(this, com.example.smartcity.activity.RegisterActivity.class);
            startActivity(intent);
        }
    }
/*
    在该方法中，要保存账号密码
 */
    private void login() throws JSONException {
        String userName = edt_name.getText().toString().trim();
        String password = edt_psw.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {//常规EditText判空
            Toast.makeText(this, "请填写用户Id", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonObject = new JSONObject();//核心代码，生成requestBody
            jsonObject.put("username", userName);
            jsonObject.put("password", password);
            RequestBody requestBody = GetRetrofit.getRequestBody(jsonObject);//一个小封装
            Call<LoginBean> call = GetRetrofit.get().getLoginBean(requestBody);
            call.enqueue(new Callback<LoginBean>() {
                @Override
                public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: 登录请求成功");
                        LoginBean loginBean = response.body();//retrofit的返回body直接是指定的bean类
                        Integer code = loginBean.getCode();
                        Log.i(TAG, "onResponse: 返回状态码：" + code);
                        if (code.equals(200)) {
                            Log.i(TAG, "onResponse: 登录成功");
                            String token = loginBean.getToken();
                            SPUtil.putString(LoginActivity.this,"token", token);
                            SPUtil.putString(LoginActivity.this,"userName", userName);
                            SPUtil.putString(LoginActivity.this,"password", password);
                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));//返回成功后跳转主活动
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginBean> call, Throwable throwable) {
                    Log.i(TAG, "onFailure: 登录请求失败");
                    Toast.makeText(LoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}