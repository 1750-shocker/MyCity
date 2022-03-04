package com.example.mycity2.activity_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycity2.R;
import com.example.mycity2.bean.CodeMsgBean;
import com.example.mycity2.util.GetRetrofit;
import com.example.mycity2.util.SPUtil;
import com.example.mycity2.util.ViewUtil;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyPswActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ModifyPswActivitykkk";
    private Toolbar toolbar;
    private TextInputLayout tlOldPsw;
    private EditText etOldPsw;
    private TextInputLayout tlNewPsw;
    private EditText etNewPsw;
    private Button btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        initView();
        setView();
    }

    private void setView() {
        btnModify.setOnClickListener(this);
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tlOldPsw = (TextInputLayout) findViewById(R.id.tl_old_psw);
        etOldPsw = (EditText) findViewById(R.id.et_old_psw);
        tlNewPsw = (TextInputLayout) findViewById(R.id.tl_new_psw);
        etNewPsw = (EditText) findViewById(R.id.et_new_psw);
        btnModify = (Button) findViewById(R.id.btn_modify);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_modify) {
            ViewUtil.hideOneInputMethod(this, etNewPsw);
            String oldPsw = etOldPsw.getText().toString();
            String newPsw = etNewPsw.getText().toString();
            if (TextUtils.isEmpty(oldPsw)) {
                tlOldPsw.setErrorEnabled(true);
                tlOldPsw.setError("请输入旧密码");
            } else if (TextUtils.isEmpty(newPsw)) {
                tlNewPsw.setErrorEnabled(true);
                tlNewPsw.setError("请输入新密码");
            } else if (!oldPsw.equals(SPUtil.getString(this, "password", ""))) {
                Toast.makeText(this, "原密码错误", Toast.LENGTH_SHORT).show();
            } else if (oldPsw.equals(newPsw)) {
                Toast.makeText(this, "两次密码一致", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("newPassword", newPsw);
                    jsonObject.put("oldPassword", oldPsw);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onClick: " + newPsw + "  " + oldPsw);
                RequestBody b = GetRetrofit.getRequestBody(jsonObject);
                new GetRetrofit(this).getWithToken().putPswBean(b).enqueue(new Callback<CodeMsgBean>() {
                    @Override
                    public void onResponse(Call<CodeMsgBean> call, Response<CodeMsgBean> response) {
                        final CodeMsgBean body = response.body();
                        final int code = body.getCode();
                        SPUtil.putString(ModifyPswActivity.this, "password", newPsw);
                        Toast.makeText(ModifyPswActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CodeMsgBean> call, Throwable throwable) {
                        Toast.makeText(ModifyPswActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}