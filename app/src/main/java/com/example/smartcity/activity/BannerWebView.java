package com.example.smartcity.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcity.R;
import com.example.smartcity.adapter.RecyclerLinearAdapter;
import com.example.smartcity.bean.NewBean;
import com.example.smartcity.bean.RowsDTO;
import com.example.smartcity.database.MDBHelper;
import com.example.smartcity.fragment.CommentDialogFragment;
import com.example.smartcity.utils.DialogFragmentDataCallBack;
import com.example.smartcity.utils.FileUtil;
import com.example.smartcity.utils.GetRetrofit;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerWebView extends AppCompatActivity implements DialogFragmentDataCallBack {
    private Toolbar toolbar;
    private TextView newsTitle;
    private TextView newsContent;
    private TextView newsElse;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private RecyclerView comments;
    private RecyclerView recommends;
    private int id;
    private TextView tvCommentFakeButton;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_webview);

        ImageView avatar = findViewById(R.id.user_avatar);
        avatar.setImageBitmap(FileUtil.getAvatar(this));

        Bundle extras = getIntent().getExtras();
        id = (Integer) extras.get("id");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvCommentFakeButton = (TextView) findViewById(R.id.tv_comment_fake_button);
        tvCommentFakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialogFragment dialogFragment = new CommentDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "Comment");
            }
        });

        comments = (RecyclerView) findViewById(R.id.comments);
        recommends = (RecyclerView) findViewById(R.id.recommends);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        comments.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        recommends.setLayoutManager(layoutManager1);
        List<RowsDTO> rows = getRandom();
        recommends.setAdapter(new RecyclerLinearAdapter(BannerWebView.this, rows));

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("????????????");

        newsTitle = (TextView) findViewById(R.id.news_title);
        newsElse = (TextView) findViewById(R.id.news_else);
        imageView = findViewById(R.id.fruit_image_view);

        mWebView = findViewById(R.id.details_content);
        mWebView.setWebViewClient(new WebViewClient());


        GetRetrofit.get().getNewBean(id).enqueue(new Callback<NewBean>() {
            @Override
            public void onResponse(Call<NewBean> call, Response<NewBean> response) {
                NewBean newB = response.body();
                if (newB != null) {
                    NewBean.DataDTO data = newB.getData();
                    if (data != null) {
                        newsTitle.setText(data.getTitle());
                        Glide.with(BannerWebView.this).load("http://124.93.196.45:10001" + data.getCover()).into(imageView);
//                        newsContent.setText(Html.fromHtml(data.getContent()));
                        mWebView.loadDataWithBaseURL("http://124.93.196.45:10001",
                                setWebVIewImage(data.getContent()), "text/html",
                                "utf-8", null);
                        newsElse.setText(data.getPublishDate());
                    } else {
                        Toast.makeText(BannerWebView.this, "??????????????????"
                                + newB.getCode(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BannerWebView.this, "??????????????????"
                            + newB.getCode(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewBean> call, Throwable throwable) {
                Toast.makeText(BannerWebView.this, "??????????????????", Toast.LENGTH_SHORT).show();
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

    // ??????image???table??????
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
        //???????????????
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setLoadWithOverviewMode(true); // ????????????????????????
        // ?????????????????? ???????????????
        mWebSettings.setUseWideViewPort(true); //????????????????????????webview?????????
        mWebSettings.setSupportZoom(true); //????????????
        mWebSettings.setJavaScriptEnabled(false);  //??????javascript
        mWebSettings.setDomStorageEnabled(true);  //??????DOM
        mWebSettings.setDefaultTextEncodingName("utf-8"); //????????????
        // // web????????????
        mWebSettings.setAllowFileAccess(true);// ???????????????

        //??????????????????????????????????????????????????????????????????????????????????????????????????????
        mWebSettings.setBlockNetworkImage(true);
        //??????????????????
        mWebSettings.setAppCacheEnabled(true);
        //??????webview
//        mWebView.setWebChromeClient(new MyWebChromeClient());
//        mWebView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public String getCommentText() {
        return tvCommentFakeButton.getText().toString();
    }

    @Override
    public void setCommentText(String commentText) {
        tvCommentFakeButton.setText(commentText);
    }
}
