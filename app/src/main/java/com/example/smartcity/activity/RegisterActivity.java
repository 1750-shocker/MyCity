package com.example.smartcity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.R;
import com.example.smartcity.bean.CommonBean;
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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "RegisterActivity";
    private EditText etName;
    private EditText etNicke;
    private EditText etPhone;
    private EditText etPassword;
    private RadioGroup rgSex;
    private RadioButton rbMan;
    private RadioButton rbWoman;
    private Button btnLogin;
    private Button btnRegister;
    String sex = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = (EditText) findViewById(R.id.et_name);
        etNicke = (EditText) findViewById(R.id.et_nicke);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etPassword = (EditText) findViewById(R.id.et_password);
        rgSex = (RadioGroup) findViewById(R.id.rg_sex);
        rbMan = (RadioButton) findViewById(R.id.rb_man);
        rbWoman = (RadioButton) findViewById(R.id.rb_woman);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        rgSex.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {

            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else if (view.getId() == R.id.btn_register) {
            goRegister();
        }
    }

    private void goRegister() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String nicke = etNicke.getText().toString().trim();
        if (TextUtils.isEmpty(nicke)) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入你的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", name);
            jsonObject.put("nickName", nicke);
            jsonObject.put("phonenumber", phone);
            jsonObject.put("sex", sex);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = GetRetrofit.getRequestBody(jsonObject);
        Call<CommonBean> call = GetRetrofit.get().getCommonBean(requestBody);
        call.enqueue(new Callback<CommonBean>() {
            @Override
            public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                //TODO:保存数据到SharedPreferences,并在LoginActivity中读取到编辑栏
                CommonBean body = response.body();
                Log.i(TAG, "onResponse: 注册请求返回：" + body.toString());
                if (body.getCode() == 200) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    SPUtil.putString(RegisterActivity.this, "userName", name);
                    SPUtil.putString(RegisterActivity.this, "nickName", nicke);
                    SPUtil.putString(RegisterActivity.this, "phoneNumber", phone);
                    SPUtil.putString(RegisterActivity.this, "sex", sex);
                    SPUtil.putString(RegisterActivity.this, "password", password);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败" + body.getCode(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonBean> call, Throwable throwable) {
                Log.i(TAG, "onFailure: " + throwable.getMessage());
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });

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