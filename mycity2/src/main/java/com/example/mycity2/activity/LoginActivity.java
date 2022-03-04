package com.example.mycity2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycity2.MainActivity;
import com.example.mycity2.R;
import com.example.mycity2.bean.LoginBean;
import com.example.mycity2.util.GetRetrofit;
import com.example.mycity2.util.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivitykk";
    private EditText edtName;
    private EditText edtPsw;
    private TextView tvRegister;
    private Button btnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setView();
    }

    private void setView() {
        fillBlank();
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void fillBlank() {
        String spUserName = SPUtil.getString(this,"username", "");
        String spPassword = SPUtil.getString(this,"password", "");//每次进入这个页面的时候读取已保存的账号密码
        edtName.setText(spUserName);
        edtPsw.setText(spPassword);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillBlank();
    }

    private void initView() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtPsw = (EditText) findViewById(R.id.edt_psw);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            login();
        } else if (view.getId() == R.id.tv_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    private void login() {
        final String username = edtName.getText().toString().trim();
        final String password = edtPsw.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "用户名未填写", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码未填写", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody requestBody = GetRetrofit.getRequestBody(jsonObject);
            GetRetrofit.get().getLoginBean(requestBody).enqueue(new Callback<LoginBean>() {
                @Override
                public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                    final LoginBean body = response.body();
                    Log.i(TAG, "onResponse: 登录请求成功，状态码："+body.getCode());
                    if (body.getCode() == 200) {
                        final String token = body.getToken();
                        SPUtil.putString(LoginActivity.this, "username", username);
                        SPUtil.putString(LoginActivity.this, "password", password);
                        SPUtil.putString(LoginActivity.this, "token", token);
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginBean> call, Throwable throwable) {
                    Log.i(TAG, "onFailure: ");
                    Toast.makeText(LoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
