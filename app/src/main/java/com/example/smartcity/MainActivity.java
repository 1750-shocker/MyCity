package com.example.smartcity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcity.activity.BannerWebView;
import com.example.smartcity.adapter.MFragmentStateAdapter;
import com.example.smartcity.bean.BannerBean;
import com.example.smartcity.bean.MessageEvent;
import com.example.smartcity.fragment.FirstPageFragment;
import com.example.smartcity.fragment.NewsFragment;
import com.example.smartcity.fragment.ServicesFragment;
import com.example.smartcity.fragment.SmartPartyFragment;
import com.example.smartcity.fragment.UserFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/*
    主页面使用ViewPager2+TabLayout组合控件实现，该实现方面设置联动和tab项
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager2 mainVp2;
    private TabLayout mainTl;
    private String[] title = {"首页", "全部服务", "智慧党建", "新闻", "个人中心"};
    private int[] icon = {R.drawable.icon1_selector, R.drawable.icon2_selector,
            R.drawable.icon3_selector, R.drawable.icon4_selector, R.drawable.icon5_selector};
    private List<View> iconItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    private void initView() {
        mainVp2 = (ViewPager2) findViewById(R.id.main_vp2);
        mainVp2.setUserInputEnabled(false);//关键代码设置Viewpager2不可滑动
        MFragmentStateAdapter mFragmentStateAdapter = new MFragmentStateAdapter(this, this);
        mainVp2.setAdapter(mFragmentStateAdapter);
        mainTl = (TabLayout) findViewById(R.id.main_tl);

        for (int i = 0; i < 5; i++) {//循环，将tab的文字和图片组合起来组合成list
            View view = View.inflate(MainActivity.this, R.layout.main_tab_item, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            TextView text = (TextView) view.findViewById(R.id.text);
            image.setImageResource(icon[i]);
            text.setText(title[i]);
            iconItem.add(view);
        }
        //关键代码，用于关联ViewPager2和TabLayout,smoothScroll=false可以取消标签切换时的动画
        new TabLayoutMediator(mainTl, mainVp2, true, false, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setCustomView(iconItem.get(position));//传入包含填充好的item的集合，在该方法关联
            }
        }).attach();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeFragment(MessageEvent event) {
        if (event.getFragmentid() == 1) {
            mainVp2.setCurrentItem(1, false);
        }
    }
}