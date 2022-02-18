package com.example.smartcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcity.R;
import com.example.smartcity.activity.AdviseActivity;
import com.example.smartcity.activity.LoginActivity;
import com.example.smartcity.activity.OrderActivity;
import com.example.smartcity.activity.UpdatePswActivity;
import com.example.smartcity.activity.UserInfoActivity;

import java.util.Objects;


public class UserFragment extends Fragment implements View.OnClickListener {
    private TextView tvUsercenter;
    private ImageButton userImg;
    private TextView tvUsername;
    private LinearLayout llInfo;
    private ImageButton ibUserInfo;
    private LinearLayout llOrder;
    private ImageButton ibOrderList;
    private LinearLayout llUpdate;
    private ImageButton ibUpdatePsw;
    private LinearLayout llAdvice;
    private ImageButton ibAdvise;
    private Button btnOut;
    private Context mContext;

    public UserFragment(Context mContext) {
        this.mContext = mContext;
    }
/*
此界面展示一个选项列表，跳转不同活动
 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        tvUsercenter = (TextView) view.findViewById(R.id.tv_usercenter);
        userImg = (ImageButton) view.findViewById(R.id.user_img);
        tvUsername = (TextView) view.findViewById(R.id.tv_username);
        llInfo = (LinearLayout) view.findViewById(R.id.ll_info);
        llInfo.setOnClickListener(this);
        ibUserInfo = (ImageButton) view.findViewById(R.id.ib_user_info);
        llOrder = (LinearLayout) view.findViewById(R.id.ll_order);
        llOrder.setOnClickListener(this);
        ibOrderList = (ImageButton) view.findViewById(R.id.ib_order_list);
        llUpdate = (LinearLayout) view.findViewById(R.id.ll_update);
        llUpdate.setOnClickListener(this);
        ibUpdatePsw = (ImageButton) view.findViewById(R.id.ib_update_psw);
        llAdvice = (LinearLayout) view.findViewById(R.id.ll_advice);
        llAdvice.setOnClickListener(this);
        ibAdvise = (ImageButton) view.findViewById(R.id.ib_advise);
        btnOut = (Button) view.findViewById(R.id.btn_out);
        btnOut.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_out) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        } else if (view.getId() == R.id.ll_info) {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ll_update) {
            Intent intent = new Intent(getActivity(), UpdatePswActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ll_advice) {
            Intent intent = new Intent(getActivity(), AdviseActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ll_order) {
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            startActivity(intent);
        }
    }
}