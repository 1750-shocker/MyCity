package com.example.smartcity.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartcity.R;
import com.example.smartcity.bean.CommonBean;
import com.example.smartcity.utils.GetRetrofit;
import com.example.smartcity.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePswActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar userInfoToolbar;
    private EditText userId;
    private EditText etOldPsw;
    private EditText etNewPsw;
    private Button btnUpdate;
    private Button btnClear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_psw);
        userInfoToolbar = (Toolbar) findViewById(R.id.user_info_toolbar);
        userInfoToolbar.setNavigationIcon(R.drawable.top_bar_left_back1);
        userInfoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userId = (EditText) findViewById(R.id.userId);
        etOldPsw = (EditText) findViewById(R.id.et_old_psw);
        etNewPsw = (EditText) findViewById(R.id.et_new_psw);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_clear) {
            userId.setText("");
            etOldPsw.setText("");
            etNewPsw.setText("");
        } else if (view.getId() == R.id.btn_update) {
            String userid = userId.getText().toString().trim();
            if (TextUtils.isEmpty(userid)) {
                Toast.makeText(this, "请填写id", Toast.LENGTH_SHORT).show();
                return;
            }
            String oldPassword = etOldPsw.getText().toString().trim();
            if (TextUtils.isEmpty(userid)) {
                Toast.makeText(this, "请填写旧密码", Toast.LENGTH_SHORT).show();
                return;
            }
            String newPassword = etNewPsw.getText().toString().trim();
            if (TextUtils.isEmpty(userid)) {
                Toast.makeText(this, "请填写新密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!oldPassword.equals(SPUtil.getString(this, "password","s"))) {
                Toast.makeText(this, "旧密码错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPassword.equals(oldPassword)) {
                Toast.makeText(this, "新密码与旧密码相同", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("newPassword", newPassword);
                jsonObject.put("oldPassword", oldPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody b = GetRetrofit.getRequestBody(jsonObject);
            new GetRetrofit(this).getWithToken().putPswBean(b).enqueue(new Callback<CommonBean>() {
                @Override
                public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                    CommonBean commonBean = response.body();
                    Toast.makeText(UpdatePswActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<CommonBean> call, Throwable throwable) {
                    Toast.makeText(UpdatePswActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
