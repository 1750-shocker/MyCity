package com.example.smartcity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartcity.R;
import com.example.smartcity.bean.CommonBean;
import com.example.smartcity.bean.UserBean;
import com.example.smartcity.utils.GetRetrofit;
import com.example.smartcity.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar userInfoToolbar;
    private ImageView ibImg;
    private EditText etNicke;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etIdetity;
    private RadioGroup rgSex;
    private RadioButton rbMan;
    private RadioButton rbWoman;
    private Button btnUpdate;
    private Button btnClear;
    private String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        userInfoToolbar = (Toolbar) findViewById(R.id.user_info_toolbar);
        userInfoToolbar.setNavigationIcon(R.drawable.top_bar_left_back1);
        userInfoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ibImg = (ImageView) findViewById(R.id.ib_img);
        etNicke = (EditText) findViewById(R.id.et_nicke);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        etIdetity = (EditText) findViewById(R.id.et_idetity);
        rgSex = (RadioGroup) findViewById(R.id.rg_sex);
        rbMan = (RadioButton) findViewById(R.id.rb_man);
        rbWoman = (RadioButton) findViewById(R.id.rb_woman);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);
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
        new GetRetrofit(this).getWithToken().getUserBean().enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                if (response.body().getUser() != null) {
                    UserBean userBean = response.body();
                    UserBean.UserDTO user = userBean.getUser();
                    etNicke.setText(user.getNickName().toString());
                    etPhone.setText(user.getPhonenumber().toString());
                    etEmail.setText(user.getEmail().toString());
                    String IDCard = user.getIdCard().toString();
                    etIdetity.setText(IDCard.substring(0,2)+"******"+ IDCard.substring(IDCard.length()-4, IDCard.length()));
                    sex = user.getSex();
                    rgSex.check(sex.equals("1") ? R.id.rb_woman : R.id.rb_man);
                } else {
                    Toast.makeText(UserInfoActivity.this,"请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_clear) {
            etEmail.setText("");
            etIdetity.setText("");
            etNicke.setText("");
            etPhone.setText("");
            sex = "";
            rgSex.clearCheck();
        } else if (view.getId() == R.id.btn_update) {
            updateInfo();
        }
    }

    private void updateInfo() {
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
        String email = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "请输入你的邮箱地址", Toast.LENGTH_SHORT).show();
            return;
        }
        String idetity = etIdetity.getText().toString().trim();
        if (TextUtils.isEmpty(idetity)) {
            Toast.makeText(this, "4405281999121411040640", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(sex)){
            Toast.makeText(this, "请选择你的性别", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickName", nicke);
            jsonObject.put("phonenumber", phone);
            jsonObject.put("email", email);
            jsonObject.put("idCard", idetity);
            jsonObject.put("sex", sex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = GetRetrofit.getRequestBody(jsonObject);
        new GetRetrofit(this).getWithToken().putUserBean(body).enqueue(new Callback<CommonBean>() {
            @Override
            public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                int code = response.body().getCode();
                if (code == 200) {
                    Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UserInfoActivity.this, "修改失败 请检查网络", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonBean> call, Throwable throwable) {
                Toast.makeText(UserInfoActivity.this, "修改失败 请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}