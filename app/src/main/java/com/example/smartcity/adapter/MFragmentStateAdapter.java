package com.example.smartcity.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.smartcity.fragment.FirstPageFragment;
import com.example.smartcity.fragment.NewsFragment;
import com.example.smartcity.fragment.ServicesFragment;
import com.example.smartcity.fragment.SmartPartyFragment;
import com.example.smartcity.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MFragmentStateAdapter extends FragmentStateAdapter {
    private Context mContext;
    public MFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, Context mContext) {
        super(fragmentActivity);
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {//有别于ViewPager的adapter用list存储每个fragment实例来返回，直接创建新的，否则banner会有错
        if (position == 0) {
            return new FirstPageFragment(mContext);
        } else if (position == 1) {
            return new ServicesFragment(mContext);
        } else if (position == 2) {
            return new SmartPartyFragment(mContext);
        } else if (position == 3) {
            return new NewsFragment(mContext);
        } else {
            return new UserFragment(mContext);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }//有几个tab就返回数字几
}
