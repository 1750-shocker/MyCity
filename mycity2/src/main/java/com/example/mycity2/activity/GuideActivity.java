package com.example.mycity2.activity;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mycity2.R;
import com.example.mycity2.adapter.GuideAdapter;
import com.example.mycity2.bean.NewsListBean;
import com.example.mycity2.bean.RowsBean;
import com.example.mycity2.bean.ServiceTable;
import com.example.mycity2.util.GetRetrofit;
import com.example.mycity2.util.MDBHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuideActivity extends AppCompatActivity {
    private ViewPager vpGuide;
    private int[] guideImages = {R.drawable.y1, R.drawable.y2, R.drawable.y3, R.drawable.y4};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vpGuide = findViewById(R.id.vp_guide);
        setView();
        initDataBase();
    }
    private void setView() {
        final GuideAdapter adapter = new GuideAdapter(this, guideImages);
        vpGuide.setAdapter(adapter);
        vpGuide.setCurrentItem(0);
    }
    private void initDataBase() {
        GetRetrofit.get().getNewsListBean().enqueue(new Callback<NewsListBean>() {
            @Override
            public void onResponse(Call<NewsListBean> call, Response<NewsListBean> response) {
                final NewsListBean body = response.body();
                final List<RowsBean> rows = body.getRows();
                final Dao<RowsBean, Integer> rowsDao = MDBHelper.getInstance(GuideActivity.this).getRowsDao();
                try {
                    rowsDao.create(rows);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NewsListBean> call, Throwable throwable) {

            }
        });
        List<ServiceTable> rows = new ArrayList<>();
        rows.add(new ServiceTable("智慧巴士", R.mipmap.bus));
        rows.add(new ServiceTable("门诊预约", R.mipmap.hospital));
        rows.add(new ServiceTable("外卖订餐", R.mipmap.waipai));
        rows.add(new ServiceTable("找房子", R.mipmap.houses));
        rows.add(new ServiceTable("找工作", R.mipmap.jobs));
        rows.add(new ServiceTable("城市地铁", R.mipmap.metro));
        rows.add(new ServiceTable("生活缴费", R.mipmap.payment));
        rows.add(new ServiceTable("看电影", R.mipmap.movies));
        rows.add(new ServiceTable("活动管理", R.mipmap.activi));
        rows.add(new ServiceTable("数据分析", R.mipmap.data));
        rows.add(new ServiceTable("停哪儿", R.mipmap.park));
        rows.add(new ServiceTable("智慧交管", R.mipmap.car));
        MDBHelper m = MDBHelper.getInstance(this);
        Dao<ServiceTable, Integer> servDao = m.getServDao();
        try {
            servDao.create(rows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




}
