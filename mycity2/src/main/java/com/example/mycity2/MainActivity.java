package com.example.mycity2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycity2.adapter.MainViewPager2Adapter;
import com.example.mycity2.util.MessageEvent;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivitykkk";
    private ViewPager2 vp2Main;
    private TabLayout tablayoutMain;
    private String[] title = {"首页", "全部服务", "智慧党建", "新闻", "个人中心"};
    private int[] icon = {R.drawable.icon1_selector, R.drawable.icon2_selector,
            R.drawable.icon3_selector, R.drawable.icon4_selector, R.drawable.icon5_selector};
    private List<View> iconItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setView();
        EventBus.getDefault().register(this);
    }
    private void initView() {
        vp2Main = (ViewPager2) findViewById(R.id.vp2_main);
        tablayoutMain = (TabLayout) findViewById(R.id.tablayout_main);
    }
    private void setView() {
        vp2Main.setUserInputEnabled(false);//设置Viewpager2不可滑动
        vp2Main.setAdapter(new MainViewPager2Adapter(this, this));

        for (int i = 0; i < 5; i++) {//循环，将tab的文字和图片组合起来组合成list
            View view = View.inflate(MainActivity.this, R.layout.item_tab_main, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            TextView text = (TextView) view.findViewById(R.id.text);
            image.setImageResource(icon[i]);
            text.setText(title[i]);
            iconItem.add(view);
        }
        //关键代码，用于关联ViewPager2和TabLayout,smoothScroll=false可以取消标签切换时的动画
        new TabLayoutMediator(tablayoutMain, vp2Main, true, false, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setCustomView(iconItem.get(position));//传入包含填充好的item的集合，在该方法关联
            }
        }).attach();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeFragment(MessageEvent event) {
        if (event.getFragmentid() == 1) {
            vp2Main.setCurrentItem(1, false);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}