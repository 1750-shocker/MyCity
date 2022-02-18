package com.example.smartcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcity.MainActivity;
import com.example.smartcity.R;
import com.example.smartcity.utils.SPUtil;

/*
闪屏页
 */
public class SplashActivity extends AppCompatActivity {

    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ll = findViewById(R.id.main_ll);
        setAlphaAnimation();//设置显示渐变动画
    }


    //设置渐变动画
    private void setAlphaAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(2500);//持续时间
        ll.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jump2Activity();//在动画结束后跳转，在此方法中根据条件（SP）来决定进入哪一个活动
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //动画结束判断是否第一次进入页面从而进入引导页或者登录页面或者主页面
    private void jump2Activity() {
        Intent intent = new Intent();
        if (SPUtil.getString(this, "isFirst", "0").equals("0")) {//是否第一次打开应用
            intent.setClass(this, com.example.smartcity.activity.GuideActivity.class);//跳转引导页
        } else if (SPUtil.getString(this, "userName", "0").equals("0")) {//根据是否保存了用户资料选择是否进入登录注册页面
            intent.setClass(this, LoginActivity.class);
        } else {
            intent.setClass(this, MainActivity.class);
        }
        startActivity(intent);
        finish();//从栈中移除该活动
    }
}
