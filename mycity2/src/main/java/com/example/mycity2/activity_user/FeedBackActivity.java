package com.example.mycity2.activity_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycity2.R;
import com.example.mycity2.bean.CodeMsgBean;
import com.example.mycity2.util.GetRetrofit;
import com.example.mycity2.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText etContent;
    private TextView tvChangedNum;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initView();
        setView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etContent = (EditText) findViewById(R.id.et_content);
        tvChangedNum = (TextView) findViewById(R.id.tv_changedNum);
        btnSend = (Button) findViewById(R.id.btn_send);
    }

    private void setView() {
        toolbar.setTitle("意见反馈");
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        btnSend.setOnClickListener(this);
        etContent.addTextChangedListener(new TextWatcher() {
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
                tvChangedNum.setText(number + "");
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
        final int viewId = view.getId();
        if (viewId == R.id.btn_send) {
            ViewUtil.hideOneInputMethod(this, etContent);
            final String content = etContent.getText().toString();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(FeedBackActivity.this, "未填写", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("content", content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = GetRetrofit.getRequestBody(jsonObject);
                new GetRetrofit(this).getWithToken().putAdviceBean(requestBody).enqueue(new Callback<CodeMsgBean>() {
                    @Override
                    public void onResponse(Call<CodeMsgBean> call, Response<CodeMsgBean> response) {
                        final CodeMsgBean body = response.body();
                        final int code = body.getCode();
                        if (code == 200) {
                            Toast.makeText(FeedBackActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FeedBackActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CodeMsgBean> call, Throwable throwable) {
                        Toast.makeText(FeedBackActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}