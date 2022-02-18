package com.example.smartcity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.smartcity.MainActivity;
import com.example.smartcity.R;
import com.example.smartcity.adapter.GuideAdapter;
import com.example.smartcity.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private static final String TAG = "GuideActivity";
    private ViewPager guideVp;
    private RadioGroup guideRg;
    private Button guideBtn;
    private List<ImageView> imageViewList;
    private LinearLayout editLinear;
    private EditText etIp;
    private EditText etPort;


    private int[] images = {R.mipmap.y1, R.mipmap.y2, R.mipmap.y3, R.mipmap.y4};//提前从服务器down的引导图
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initPage();
        initView();


    }
/*
使用ViewPager和RadioGroup联动实现圆点指示器
 */
    private void initView() {
        guideVp = (ViewPager) findViewById(R.id.guide_vp);
        guideRg = (RadioGroup) findViewById(R.id.guide_rg);
        guideBtn = (Button) findViewById(R.id.guide_btn);
        editLinear = (LinearLayout) findViewById(R.id.edit_linear);
        etIp = (EditText) findViewById(R.id.et_ip);
        etPort = (EditText) findViewById(R.id.et_port);
        guideVp.setAdapter(new GuideAdapter(imageViewList));
        //ViewPager和RadioGroup分别设置影响对方的监听
        guideVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                guideRg.check(guideRg.getChildAt(position).getId());//关键方法
                //根据是否时最后一页来设置“进入按钮”是否显示
                if (position == guideRg.getChildCount() - 1) {
                    guideBtn.setVisibility(View.VISIBLE);
                    editLinear.setVisibility(View.VISIBLE);
                } else {
                    guideBtn.setVisibility(View.GONE);
                    editLinear.setVisibility(View.GONE);
                }
            }
        });
        guideRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                for (int pos = 0; pos < guideRg.getChildCount(); pos++) {
                    if (guideRg.getChildAt(pos).getId() == i) {
                        guideVp.setCurrentItem(pos);//关键方法
                    }
                }
            }
        });
        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtil.putString(GuideActivity.this,"isFirst", "1");//关键代码，一旦结束引导页，要讲SP是否第一次应用改为否
                if (!TextUtils.isEmpty(etIp.getText().toString()) && !TextUtils.isEmpty(etPort.getText().toString())) {
                    SPUtil.putString(GuideActivity.this, "ip",etIp.getText().toString());
                    SPUtil.putString(GuideActivity.this, "ip",etPort.getText().toString());
                    Log.i(TAG, "onClick: ip和port保存成功");
                }
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
/*
该方法用于将引导页图片生成集合来传参
 */
    private void initPage() {
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        imageViewList = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            ImageView view = new ImageView(this);
            view.setLayoutParams(params);
            view.setImageResource(images[i]);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewList.add(view);
        }
    }
}
