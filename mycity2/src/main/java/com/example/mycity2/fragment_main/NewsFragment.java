package com.example.mycity2.fragment_main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mycity2.R;
import com.example.mycity2.adapter.NewsTabAdapter;
import com.example.mycity2.fragment.NewsListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragmentkkk";
    private LinearLayout news;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_main, container, false);
        news = (LinearLayout) view.findViewById(R.id.news);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] title = {"今日要闻", "专题聚焦", "政策解读", "经济发展", "文化旅游", "科技创新"};
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            fragmentList.add(NewsListFragment.newInstance(i));
        }
        viewPager.setAdapter(new NewsTabAdapter(getChildFragmentManager(), fragmentList, title));
        tabLayout.setupWithViewPager(viewPager);
    }
}
