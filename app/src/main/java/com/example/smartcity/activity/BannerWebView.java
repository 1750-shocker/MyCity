package com.example.smartcity.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartcity.R;
import com.example.smartcity.bean.NewBean;
import com.example.smartcity.utils.GetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerWebView extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView newsTitle;
    private TextView newsContent;
    private TextView newsElse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_webview);
        Bundle extras = getIntent().getExtras();
        int id = (Integer)extras.get("id");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.top_bar_left_back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        newsTitle = (TextView) findViewById(R.id.news_title);
        newsContent = (TextView) findViewById(R.id.news_content);
        newsElse = (TextView) findViewById(R.id.news_else);
        GetRetrofit.get().getNewBean(id).enqueue(new Callback<NewBean>() {
            @Override
            public void onResponse(Call<NewBean> call, Response<NewBean> response) {
                NewBean newB = response.body();
                if (newB != null) {
                    NewBean.DataDTO data = newB.getData();
                    if (data != null) {
                        newsTitle.setText(data.getTitle());
                        newsContent.setText(Html.fromHtml(data.getContent()));
                        newsElse.setText(data.getPublishDate());
                    } else {
                        Toast.makeText(BannerWebView.this, newB.getCode() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BannerWebView.this, "responseBody为空", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewBean> call, Throwable throwable) {

            }
        });
    }
}
