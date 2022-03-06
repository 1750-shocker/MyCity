package com.example.mycity2.activity_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mycity2.R;
import com.example.mycity2.bean.CodeMsgBean;
import com.example.mycity2.bean.UserBean;
import com.example.mycity2.bean.UserInfoBean;
import com.example.mycity2.util.GetRetrofit;
import com.example.mycity2.util.ViewUtil;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {
    private static final String TAG = "UserInfoActivitykkk";
    private Toolbar toolbar;
    private TextInputLayout tlNickname;
    private EditText etNickname;
    private TextInputLayout tlPhonenumber;
    private EditText etPhonenumber;
    private TextInputLayout tlEmail;
    private EditText etEmail;
    private TextInputLayout tlIdcard;
    private EditText etIdcard;
    private RadioGroup rgSex;
    private RadioButton rbMan;
    private RadioButton rbWoman;
    private Button btnUpdate;
    private String sex = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        setView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tlNickname = (TextInputLayout) findViewById(R.id.tl_nickname);
        etNickname = (EditText) findViewById(R.id.et_nickname);
        tlPhonenumber = (TextInputLayout) findViewById(R.id.tl_phonenumber);
        etPhonenumber = (EditText) findViewById(R.id.et_phonenumber);
        tlEmail = (TextInputLayout) findViewById(R.id.tl_email);
        etEmail = (EditText) findViewById(R.id.et_email);
        tlIdcard = (TextInputLayout) findViewById(R.id.tl_idcard);
        etIdcard = (EditText) findViewById(R.id.et_idcard);
        rgSex = (RadioGroup) findViewById(R.id.rg_sex);
        rbMan = (RadioButton) findViewById(R.id.rb_man);
        rbWoman = (RadioButton) findViewById(R.id.rb_woman);
        btnUpdate = (Button) findViewById(R.id.btn_update);
    }

    private void setView() {
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUtil.hideOneInputMethod(UserInfoActivity.this, etEmail);
                final Editable idcardText = etIdcard.getText();
                final Editable emailText = etEmail.getText();
                final Editable nicknameText = etNickname.getText();
                final Editable phonenumberText = etPhonenumber.getText();
                if (TextUtils.isEmpty(nicknameText)) {
                    tlNickname.setErrorEnabled(true);
                    tlNickname.setError("请输入昵称");
                } else if (TextUtils.isEmpty(phonenumberText)) {
                    tlPhonenumber.setErrorEnabled(true);
                    tlPhonenumber.setError("请输入电话号码");
                } else if (TextUtils.isEmpty(emailText)) {
                    tlEmail.setErrorEnabled(true);
                    tlEmail.setError("请输入电子邮箱");
                } else if (TextUtils.isEmpty(idcardText)) {
                    tlIdcard.setErrorEnabled(true);
                    tlIdcard.setError("请输入身份证号码");
                } else {
                    final JSONObject object = new JSONObject();
                    try {
                        object.put("nickName", nicknameText);
                        object.put("phonenumber", phonenumberText);
                        object.put("email", emailText);
                        object.put("idCard", idcardText);
                        object.put("sex", sex);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final RequestBody requestBody = GetRetrofit.getRequestBody(object);
                    new GetRetrofit(UserInfoActivity.this).getWithToken().putUserBean(requestBody).enqueue(new Callback<CodeMsgBean>() {
                        @Override
                        public void onResponse(Call<CodeMsgBean> call, Response<CodeMsgBean> response) {
                            final CodeMsgBean body = response.body();
                            if (body.getCode() == 200) {
                                Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserInfoActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CodeMsgBean> call, Throwable throwable) {
                            Toast.makeText(UserInfoActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbMan.isChecked()) {
                    sex = "0";
                } else {
                    sex = "1";
                }
            }
        });
        new GetRetrofit(this).getWithToken().getUserInfoBean().enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                final UserInfoBean body = response.body();
                if (body.getCode() == 200) {
                    final UserBean userBean = body.getUser();
                    etEmail.setText(userBean.getEmail());
                    String idCard = userBean.getIdCard();
                    if (!TextUtils.isEmpty(idCard)) {
                        etIdcard.setText(idCard.substring(0, 2) + "*********" + idCard.substring(idCard.length() - 4, idCard.length()));
                    } else {
                        etIdcard.setText("");
                    }
                    etNickname.setText(userBean.getNickName());
                    etPhonenumber.setText(userBean.getPhonenumber());
                    sex = userBean.getSex();
                    rgSex.check(sex.equals("1") ? R.id.rb_woman : R.id.rb_man);
                } else {
                    Toast.makeText(UserInfoActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable throwable) {
                Toast.makeText(UserInfoActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}