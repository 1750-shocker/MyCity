package com.example.smartcity.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


public class NewsTabAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private String[] titles;

    public NewsTabAdapter(@NonNull FragmentManager fm,List<Fragment> fragmentList,String[] titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }//关键代码，每个位置对应不同的fragment，再在不同的fragment的onCreateActivity中加载数据生成新闻列表，
    // 计划用数据库提高刷新速度，虽然逻辑上还是冗余加载

    /**
     * fragment中的个数
     */
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    /**
     * 返回当前的标题
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }//关键代码，传入标题的数组
}

