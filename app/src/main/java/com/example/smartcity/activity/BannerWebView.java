package com.example.smartcity.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    private WebView mWebView;
    private WebSettings mWebSettings;

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
                        mWebView.loadDataWithBaseURL("http://124.93.196.45:10001", setWebVIewImage(data.getContent()), "text/html" , "utf-8", null);
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
    // 适配image和table标签
    public static String setWebVIewImage(String star) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:auto; height:auto;}</style>"
                + "<style>table{max-width: 100%; width:auto; height:auto;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + star + "</body></html>";
    }
    private void setWebView(){
        mWebSettings = mWebView.getSettings();
        //自适应屏幕
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 打开页面时， 自适应屏幕
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setSupportZoom(true); //支持缩放
        mWebSettings.setJavaScriptEnabled(true);  //开启javascript
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
