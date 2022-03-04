package com.example.mycity2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mycity2.R;
import com.example.mycity2.bean.CodeMsgBean;
import com.example.mycity2.util.GetRetrofit;
import com.example.mycity2.util.SPUtil;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "RegisterActivitykkk";
    private TextInputLayout tlUsername;
    private EditText etUsername;
    private TextInputLayout tlNickname;
    private EditText etNickname;
    private TextInputLayout tlPhonenumber;
    private EditText etPhonenumber;
    private TextInputLayout tlPassword;
    private EditText etPassword;
    private RadioGroup rgSex;
    private RadioButton rbMan;
    private RadioButton rbWoman;
    private Button btnRegister;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setView();
    }

    private void setView() {
        rgSex.setOnCheckedChangeListener(this);
        btnRegister.setOnClickListener(this);
        rgSex.check(R.id.rb_woman);
    }

    private void initView() {
        tlUsername = (TextInputLayout) findViewById(R.id.tl_username);
        etUsername = (EditText) findViewById(R.id.et_username);
        tlNickname = (TextInputLayout) findViewById(R.id.tl_nickname);
        etNickname = (EditText) findViewById(R.id.et_nickname);
        tlPhonenumber = (TextInputLayout) findViewById(R.id.tl_phonenumber);
        etPhonenumber = (EditText) findViewById(R.id.et_phonenumber);
        tlPassword = (TextInputLayout) findViewById(R.id.tl_password);
        etPassword = (EditText) findViewById(R.id.et_password);
        rgSex = (RadioGroup) findViewById(R.id.rg_sex);
        rbMan = (RadioButton) findViewById(R.id.rb_man);
        rbWoman = (RadioButton) findViewById(R.id.rb_woman);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_register) {
            String username = etUsername.getText().toString().trim();
            String nickname = etNickname.getText().toString().trim();
            String phonenumber = etPhonenumber.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                tlUsername.setErrorEnabled(true);
                tlUsername.setError("请输入用户名");
            } else if (TextUtils.isEmpty(nickname)) {
                tlNickname.setErrorEnabled(true);
                tlNickname.setError("请输入昵称");
            } else if (TextUtils.isEmpty(phonenumber)) {
                tlPhonenumber.setErrorEnabled(true);
                tlPhonenumber.setError("请输入手机号码");
            } else if (TextUtils.isEmpty(password)) {
                tlPassword.setErrorEnabled(true);
                tlPassword.setError("请输入密码");
            } else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userName", username);
                    jsonObject.put("nickName", nickname);
                    jsonObject.put("phonenumber", phonenumber);
                    jsonObject.put("sex", sex);
                    jsonObject.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final RequestBody requestBody = GetRetrofit.getRequestBody(jsonObject);
                GetRetrofit.get().getRegisterBean(requestBody).enqueue(new Callback<CodeMsgBean>() {
                    @Override
                    public void onResponse(Call<CodeMsgBean> call, Response<CodeMsgBean> response) {
                        final CodeMsgBean body = response.body();
                        if (body.getCode() == 200) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            SPUtil.putString(RegisterActivity.this, "username", username);
                            SPUtil.putString(RegisterActivity.this, "nickname", nickname);
                            SPUtil.putString(RegisterActivity.this, "phonenumber", phonenumber);
                            SPUtil.putString(RegisterActivity.this, "sex", sex);
                            SPUtil.putString(RegisterActivity.this, "password", password);
//                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Log.i(TAG, "onResponse: 请求成功，处理失败，code：" + body.getCode());
                            Toast.makeText(RegisterActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CodeMsgBean> call, Throwable throwable) {
                        Toast.makeText(RegisterActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.rb_man) {
            sex = "0";
        } else if (i == R.id.rb_woman) {
            sex = "1";
        }
    }
}