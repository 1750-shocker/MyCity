package com.example.mycity2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycity2.MainActivity;
import com.example.mycity2.R;
import com.example.mycity2.util.SPUtil;

public class SplashActivity extends AppCompatActivity {

    private ImageView ivSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivSplash = (ImageView) findViewById(R.id.iv_splash);
        setAlphaAnimation();
    }

    //设置渐变动画
    private void setAlphaAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.5f, 1.0f);
        animation.setDuration(1800);//持续时间
        ivSplash.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jump2Activity();
                //在动画结束后跳转，在此方法中根据条件（SP）来决定进入哪一个活动
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //动画结束判断是否第一次进入页面从而进入引导页或者登录页面或者主页面
    private void jump2Activity() {
        Intent intent = new Intent();
        if (SPUtil.getString(this, "isFirst", "0").equals("0")) {
            //是否第一次打开应用
            intent.setClass(this, GuideActivity.class);//跳转引导页
        } else if (SPUtil.getString(this, "username", "0").equals("0")) {
            //根据是否保存了用户资料选择是否进入登录注册页面
            intent.setClass(this, LoginActivity.class);
        } else {
            intent.setClass(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
