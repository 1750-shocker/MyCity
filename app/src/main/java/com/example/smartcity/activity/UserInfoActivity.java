package com.example.smartcity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartcity.R;
import com.example.smartcity.bean.CommonBean;
import com.example.smartcity.bean.UserBean;
import com.example.smartcity.utils.BitmapUtil;
import com.example.smartcity.utils.FileUtil;
import com.example.smartcity.utils.GetRetrofit;
import com.example.smartcity.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
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
    private int COMBINE_CODE = 4; // 既可拍照获得现场图片、也可在相册挑选已有图片的请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        LinearLayout changeAvatar = findViewById(R.id.change_avatar);
        changeAvatar.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        ibImg = (ImageView) findViewById(R.id.ib_img);
        ibImg.setImageBitmap(FileUtil.getAvatar(this));

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
                    etIdetity.setText(IDCard.substring(0, 2) + "*********" + IDCard.substring(IDCard.length() - 4, IDCard.length()));
                    sex = user.getSex();
                    rgSex.check(sex.equals("1") ? R.id.rb_woman : R.id.rb_man);
                } else {
                    Toast.makeText(UserInfoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable throwable) {

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
        } else if (view.getId() == R.id.change_avatar) {
            openSelectDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == COMBINE_CODE) { // 从组合选择返回
            if (intent.getData() != null) { // 从相册选择一张照片
                Uri uri = intent.getData(); // 获得已选择照片的路径对象
                // 根据指定图片的uri，获得自动缩小后的位图对象
                Bitmap bitmap = BitmapUtil.getAutoZoomImage(this, uri);
                ibImg.setImageBitmap(bitmap); // 设置图像视图的位图对象
                final String s = FileUtil.bitmapToString(bitmap);
                SPUtil.putString(this, "avatar", s);
            } else if (intent.getExtras() != null) { // 拍照的缩略图
                Object obj = intent.getExtras().get("data");
                if (obj instanceof Bitmap) { // 属于位图类型
                    Bitmap bitmap = (Bitmap) obj; // 强制转成位图对象
                    ibImg.setImageBitmap(bitmap); // 设置图像视图的位图对象
                    final String s = FileUtil.bitmapToString(bitmap);
                    SPUtil.putString(this, "avatar", s);
                }
            }
        }
    }

    // 打开选择对话框（要拍照还是去相册）
    private void openSelectDialog() {
        // 声明相机的拍照行为
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent[] intentArray = new Intent[]{photoIntent};
        // 声明相册的打开行为
        Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        albumIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false); // 是否允许多选
        albumIntent.setType("image/*"); // 类型为图像
        // 容纳相机和相册在内的选择意图
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "请拍照或选择图片");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, albumIntent);
        // 创建封装好标题的选择器意图
        Intent chooser = Intent.createChooser(chooserIntent, "选择图片");
        // 在页面底部弹出多种选择方式的列表对话框
        startActivityForResult(chooser, COMBINE_CODE);
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
        if (TextUtils.isEmpty(sex)) {
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
            jsonObject.put("avatar", SPUtil.getString(this, "avatar", "ddd"));
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
                    Toast.makeText(UserInfoActivity.this, "返回值：" + code + ":"
                            + SPUtil.getString(UserInfoActivity.this, "avatar", "kk")
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonBean> call, Throwable throwable) {
                Toast.makeText(UserInfoActivity.this, "修改失败 请检查网络Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}