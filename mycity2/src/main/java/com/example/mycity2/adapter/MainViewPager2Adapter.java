package com.example.mycity2.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mycity2.fragment_main.HomePageFragment;
import com.example.mycity2.fragment_main.MiddleFragment;
import com.example.mycity2.fragment_main.NewsFragment;
import com.example.mycity2.fragment_main.ServiceFragment;
import com.example.mycity2.fragment_main.UserFragment;

public class MainViewPager2Adapter extends FragmentStateAdapter {
    private static final String TAG = "MainViewPager2Adapterkkk";
    private Context mContext;
    public MainViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, Context context) {
        super(fragmentActivity);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new HomePageFragment();
        } else if (position == 1) {
            return new ServiceFragment();
        } else if (position == 2) {
            return new MiddleFragment();
        } else if (position == 3) {
            return new NewsFragment();
        } else {
            return new UserFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
