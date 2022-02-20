package com.example.smartcity.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartcity.R;
import com.example.smartcity.bean.CommonBean;
import com.example.smartcity.utils.GetRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdviseActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbarAdvice;
    private EditText content;
    private Button btnSend;
    private TextView changedNum;
    private int maxNum = 150;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        initView();

    }

    private void initView() {
        toolbarAdvice = (Toolbar) findViewById(R.id.toolbar_advice);
        toolbarAdvice.setNavigationIcon(R.drawable.top_bar_left_back1);
        toolbarAdvice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarAdvice.setTitle("意见反馈");
        content = (EditText) findViewById(R.id.content);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        changedNum = findViewById(R.id.changedNum);
        content.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int number = editable.length();
                changedNum.setText(number+"");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send) {
            String content = this.content.getText().toString();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "未填写", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("content", content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = GetRetrofit.getRequestBody(jsonObject);
            new GetRetrofit(this).getWithToken().putAdviceBean(body).enqueue(new Callback<CommonBean>() {//这个方法适用于需要token的请求
                @Override
                public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                    CommonBean body1 = response.body();
                    int code = body1.getCode();
                    if (code == 200) {
                        Toast.makeText(AdviseActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdviseActivity.this, "提交失败 " + code, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CommonBean> call, Throwable throwable) {
                    Toast.makeText(AdviseActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
