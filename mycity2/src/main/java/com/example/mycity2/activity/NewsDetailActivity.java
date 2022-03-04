package com.example.mycity2.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycity2.R;
import com.example.mycity2.adapter.NewsListAdapter;
import com.example.mycity2.bean.RowsBean;
import com.example.mycity2.fragment.CommentDialogFragment;
import com.example.mycity2.util.DialogFragmentDataCallBack;
import com.example.mycity2.util.MDBHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsDetailActivity extends AppCompatActivity implements DialogFragmentDataCallBack {

    private AppBarLayout appBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView ivCover;
    private Toolbar toolbar;
    private TextView newsTitle;
    private WebView webViewContent;
    private TextView newsDate;
    private RecyclerView rvRecommend;
    private RecyclerView rvComment;
    private ImageView userAvatar;
    private TextView tvCommentFakeButton;
    private int id;
    private RowsBean rowsBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        id = getIntent().getIntExtra("id", 29);
        initView();
        setView();
    }

    private void initView() {

        appBar = (AppBarLayout) findViewById(R.id.appBar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        newsTitle = (TextView) findViewById(R.id.news_detail_title);
        webViewContent = (WebView) findViewById(R.id.webView_content);
        newsDate = (TextView) findViewById(R.id.news_date);
        rvRecommend = (RecyclerView) findViewById(R.id.rv_recommend);
        rvComment = (RecyclerView) findViewById(R.id.rv_comment);
        userAvatar = (ImageView) findViewById(R.id.user_avatar);
        tvCommentFakeButton = (TextView) findViewById(R.id.tv_comment_fake_button);
    }
    private void setView() {
        try {
            final List<RowsBean> rowsBeans = MDBHelper.getInstance(this).getRowsDao().queryBuilder().where().eq("id", id).query();
            rowsBean = rowsBeans.get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        rvRecommend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRecommend.setAdapter(new NewsListAdapter(this, getRandomRows()));
        newsTitle.setText(rowsBean.getTitle());
        Glide.with(this).load("http://124.93.196.45:10001" + rowsBean.getCover()).into(ivCover);
        webViewContent.loadDataWithBaseURL("http://124.93.196.45:10001",
                setWebVIewImage(rowsBean.getContent()), "text/html",
                "utf-8", null);
        newsDate.setText(rowsBean.getPublishDate());
        userAvatar.setImageResource(R.drawable.y1);

        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        tvCommentFakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommentDialogFragment().show(getSupportFragmentManager(), "Comment");
            }
        });
        rvComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        collapsingToolbar.setTitle("新闻详情");

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    private List<RowsBean> getRandomRows() {
        List<RowsBean> rows = new ArrayList<>();
        try {
            List<RowsBean> rowsBean = MDBHelper.getInstance(this).getRowsDao().queryForAll();
            Random random = new Random();
            int min = 0;
            int max = rowsBean.size();
            int num1 = random.nextInt(max) % (max - min + 1) + min;
            int num2 = random.nextInt(max) % (max - min + 1) + min;
            int num3 = random.nextInt(max) % (max - min + 1) + min;
            while (num1 == num2 || num1 == num3 || num3 == num2 || num1 == id || num2 == id || num3 == id) {
                num1 = random.nextInt(max) % (max - min + 1) + min;
                num2 = random.nextInt(max) % (max - min + 1) + min;
                num3 = random.nextInt(max) % (max - min + 1) + min;
            }
            RowsBean row1 = rowsBean.get(num1);
            RowsBean row2 = rowsBean.get(num2);
            RowsBean row3 = rowsBean.get(num3);

            rows.add(row1);
            rows.add(row2);
            rows.add(row3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
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
