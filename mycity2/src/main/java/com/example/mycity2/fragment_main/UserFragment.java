package com.example.mycity2.fragment_main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycity2.MainActivity;
import com.example.mycity2.R;
import com.example.mycity2.activity.LoginActivity;
import com.example.mycity2.activity_user.FeedBackActivity;
import com.example.mycity2.activity_user.ModifyPswActivity;
import com.example.mycity2.activity_user.OrderListActivity;
import com.example.mycity2.activity_user.UserInfoActivity;
import com.example.mycity2.util.SPUtil;

public class UserFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "UserFragmentkkk";
    private ImageView ivAvatar;
    private TextView tvUsername;
    private RelativeLayout llUserInfo;
    private RelativeLayout llOrderList;
    private RelativeLayout llChangePsw;
    private RelativeLayout llFeedBack;
    private Button btnLogOut;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
        tvUsername = (TextView) view.findViewById(R.id.tv_username);
        llUserInfo = (RelativeLayout) view.findViewById(R.id.ll_user_info);
        llOrderList = (RelativeLayout) view.findViewById(R.id.ll_order_list);
        llChangePsw = (RelativeLayout) view.findViewById(R.id.ll_change_psw);
        llFeedBack = (RelativeLayout) view.findViewById(R.id.ll_feed_back);
        btnLogOut = (Button) view.findViewById(R.id.btn_log_out);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        ivAvatar.setImageResource(R.drawable.y1);
        tvUsername.setText(SPUtil.getString(mContext, "username", ""));
        llChangePsw.setOnClickListener(this);
        llFeedBack.setOnClickListener(this);
        llUserInfo.setOnClickListener(this);
        llOrderList.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();
        if (viewId == R.id.iv_avatar || viewId == R.id.ll_user_info) {
            mContext.startActivity(new Intent(mContext, UserInfoActivity.class));
        } else if (viewId == R.id.ll_change_psw) {
            mContext.startActivity(new Intent(mContext, ModifyPswActivity.class));
        } else if (viewId == R.id.ll_feed_back) {
            mContext.startActivity(new Intent(mContext, FeedBackActivity.class));
        } else if (viewId == R.id.ll_order_list) {
            mContext.startActivity(new Intent(mContext, OrderListActivity.class));
        } else if (viewId == R.id.btn_log_out) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
            ((MainActivity)mContext).finish();
        }
    }
}
