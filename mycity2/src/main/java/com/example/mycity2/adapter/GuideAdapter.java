package com.example.mycity2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mycity2.MainActivity;
import com.example.mycity2.R;
import com.example.mycity2.activity.GuideActivity;
import com.example.mycity2.activity.LoginActivity;
import com.example.mycity2.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends PagerAdapter {
    private ImageView ivGuide;
    private RadioGroup rgIndicator;
    private Button btnStart;

    private List<View> mViewList = new ArrayList<>();

    public GuideAdapter(final Context context, int[] imageArray) {

        for (int i = 0; i < imageArray.length; i++) {
            //从资源文件中生成视图对象
            View view = LayoutInflater.from(context).inflate(R.layout.item_guide, null);
            ivGuide = (ImageView) view.findViewById(R.id.iv_guide);
            rgIndicator = (RadioGroup) view.findViewById(R.id.rg_indicator);
            btnStart = (Button) view.findViewById(R.id.btn_start);
            ivGuide.setImageResource(imageArray[i]);
            //每个页面分配一个对应的单选按钮
            for (int j = 0; j < imageArray.length; j++) {
                final RadioButton radioButton = new RadioButton(context);
                radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setButtonDrawable(R.drawable.seletor_guide_indicator);
                radioButton.setPadding(10, 10, 10, 10);
                rgIndicator.addView(radioButton);
            }
            ((RadioButton) rgIndicator.getChildAt(i)).setChecked(true);
            if (i == imageArray.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.putString(context, "isFirst", "1");
                        context.startActivity(new Intent(context, LoginActivity.class));
                        ((GuideActivity) context).finish();
                    }
                });
            }
            mViewList.add(view);
        }
    }


    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }
}
