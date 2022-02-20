package com.example.smartcity.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.smartcity.bean.MessageEvent;
import com.example.smartcity.fragment.NewsFragment;
import com.example.smartcity.fragment.Page1NewsFragment;

import org.greenrobot.eventbus.EventBus;

public class M1FragmentStateAdapter extends FragmentStateAdapter {
    Context mContext;
    public M1FragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, Context context) {
        super(fragmentActivity);
        this.mContext = context;
//        EventBus.getDefault().post(new MessageEvent(2));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new Page1NewsFragment(mContext);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
