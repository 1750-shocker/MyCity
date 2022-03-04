package com.example.smartcity.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.smartcity.R;
import com.example.smartcity.adapter.NewsTabAdapter;
import com.example.smartcity.bean.MessageEvent;
import com.example.smartcity.bean.NewsBean;
import com.example.smartcity.bean.RowsDTO;
import com.example.smartcity.database.MDBHelper;
import com.example.smartcity.utils.GetRetrofit;
import com.google.android.material.tabs.TabLayout;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    private static final String TAG = "aaa";
    private LinearLayout news;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext;

    public NewsFragment(Context mContext) {
        this.mContext = mContext;
    }

    /*
     TabLayout+ViewPager实现标签和列表联动
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        news = (LinearLayout) view.findViewById(R.id.news);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] title = {"今日要闻", "专题聚焦", "政策解读", "经济发展", "文化旅游", "科技创新"};//tabItem数组
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(JingriFragment.newInstance());
        fragmentList.add(ZhuantiFragment.newInstance());
        fragmentList.add(ZhengceFragment.newInstance());
        fragmentList.add(JingjiFragment.newInstance());
        fragmentList.add(WenhuaFragment.newInstance());
        fragmentList.add(KejiFragment.newInstance());//ViewPager的adapter是需要一个fragment实例的集合来返回
        NewsTabAdapter newsTabAdapter = new NewsTabAdapter(getChildFragmentManager(), fragmentList, title);
        viewPager.setAdapter(newsTabAdapter);
        tabLayout.setupWithViewPager(viewPager);//关键代码，实现TabLayout和ViewPager一键联动，但是生成item项是在adapter中实现的
    }

}