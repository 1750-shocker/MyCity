package com.example.smartcity.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.adapter.RecyclerLinearAdapter;
import com.example.smartcity.bean.NewBean;
import com.example.smartcity.bean.RowsDTO;
import com.example.smartcity.database.MDBHelper;
import com.example.smartcity.utils.GetRetrofit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerWebView extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView newsTitle;
    private TextView newsContent;
    private TextView newsElse;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private RecyclerView comments;
    private RecyclerView recommends;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_webview);
        Bundle extras = getIntent().getExtras();
        id = (Integer) extras.get("id");
        comments = (RecyclerView) findViewById(R.id.comments);
        recommends = (RecyclerView) findViewById(R.id.recommends);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        comments.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        recommends.setLayoutManager(layoutManager1);
        List<RowsDTO> rows = getRandom();
        recommends.setAdapter(new RecyclerLinearAdapter(BannerWebView.this, rows));
        //TODO:comments的数据请求&adapter&itemLayout没写
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.top_bar_left_back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        newsTitle = (TextView) findViewById(R.id.news_title);
//        newsContent = (TextView) findViewById(R.id.news_content);
        mWebView = findViewById(R.id.details_content);
        mWebView.setWebViewClient(new WebViewClient());
        newsElse = (TextView) findViewById(R.id.news_else);
        GetRetrofit.get().getNewBean(id).enqueue(new Callback<NewBean>() {
            @Override
            public void onResponse(Call<NewBean> call, Response<NewBean> response) {
                NewBean newB = response.body();
                if (newB != null) {
                    NewBean.DataDTO data = newB.getData();
                    if (data != null) {
                        newsTitle.setText(data.getTitle());
//                        newsContent.setText(Html.fromHtml(data.getContent()));
                        mWebView.loadDataWithBaseURL("http://124.93.196.45:10001",
                                setWebVIewImage(data.getContent()), "text/html",
                                "utf-8", null);
                        newsElse.setText(data.getPublishDate());
                    } else {
                        Toast.makeText(BannerWebView.this, "网络请求失败"
                                + newB.getCode(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BannerWebView.this, "网络请求失败"
                            + newB.getCode(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewBean> call, Throwable throwable) {
                Toast.makeText(BannerWebView.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<RowsDTO> getRandom() {
        List<RowsDTO> rows = new ArrayList<>();
        try {
            List<RowsDTO> rowsDTOS = MDBHelper.getInstance(this).getNewsDao().queryForAll();
            Random random = new Random();
            int min = 0;
            int max = rowsDTOS.size();
            int num1 = random.nextInt(max) % (max - min + 1) + min;
            int num2 = random.nextInt(max) % (max - min + 1) + min;
            int num3 = random.nextInt(max) % (max - min + 1) + min;
            while (num1 == num2 || num1 == num3 || num3 == num2 || num1 == id || num2 == id || num3 == id) {
                num1 = random.nextInt(max) % (max - min + 1) + min;
                num2 = random.nextInt(max) % (max - min + 1) + min;
                num3 = random.nextInt(max) % (max - min + 1) + min;
            }
            RowsDTO row1 = rowsDTOS.get(num1);
            RowsDTO row2 = rowsDTOS.get(num2);
            RowsDTO row3 = rowsDTOS.get(num3);

            rows.add(row1);
            rows.add(row2);
            rows.add(row3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    // 适配image和table标签
    public static String setWebVIewImage(String star) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:auto; height:auto;}</style>"
                + "<style>table{max-width: 100%; width:auto; height:auto;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + star + "</body></html>";
    }

    private void setWebView() {
        mWebSettings = mWebView.getSettings();
        //自适应屏幕
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 打开页面时， 自适应屏幕
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setSupportZoom(true); //支持缩放
        mWebSettings.setJavaScriptEnabled(false);  //开启javascript
        mWebSettings.setDomStorageEnabled(true);  //开启DOM
        mWebSettings.setDefaultTextEncodingName("utf-8"); //设置编码
        // // web页面处理
        mWebSettings.setAllowFileAccess(true);// 支持文件流

        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，再进行加载图片
        mWebSettings.setBlockNetworkImage(true);
        //开启缓存机制
        mWebSettings.setAppCacheEnabled(true);
        //设置webview
//        mWebView.setWebChromeClient(new MyWebChromeClient());
//        mWebView.setWebViewClient(new MyWebViewClient());
    }
}
