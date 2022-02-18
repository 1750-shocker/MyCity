package com.example.smartcity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.MainActivity;
import com.example.smartcity.R;
import com.example.smartcity.fragment.ServicesFragment;
import com.example.smartcity.serv_activity.ActActivity;
import com.example.smartcity.serv_activity.BusActivity;
import com.example.smartcity.serv_activity.FeeActivity;
import com.example.smartcity.serv_activity.HospitalActivity;
import com.example.smartcity.serv_activity.HouseActivity;
import com.example.smartcity.serv_activity.JobActivity;
import com.example.smartcity.serv_activity.MetroActivity;
import com.example.smartcity.serv_activity.MovieActivity;
import com.example.smartcity.serv_activity.ParkActivity;
import com.example.smartcity.serv_activity.TakeoutActivity;
import com.example.smartcity.serv_activity.TraficActivity;
import com.j256.ormlite.stmt.query.In;

public class GridAdapter extends RecyclerView.Adapter {
    private static final String TAG = "gridAdapter";
    private Context mContext;
    private int[] gridImgArray = {R.mipmap.pic_01, R.mipmap.pic_02, R.mipmap.pic_03, R.mipmap.pic_04,
            R.mipmap.pic_05, R.mipmap.pic_06, R.mipmap.pic_07, R.mipmap.pic_08, R.mipmap.pic_09, R.mipmap.pic_10};
    private String[] gridTitleArray = {"停哪儿", "城市地铁", "外卖订餐", "找工作", "找房子", "智慧交警",
            "活动管理", "生活交费", "看电影", "更多"};

    public GridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.grid_item,parent,false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.ivPic.setImageResource(gridImgArray[position]);
        itemHolder.tvTitle.setText(gridTitleArray[position]);
        itemHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    mContext.startActivity(new Intent(mContext, ParkActivity.class));
                } else if (position == 1) {
                    mContext.startActivity(new Intent(mContext, MetroActivity.class));
                } else if (position == 2) {
                    mContext.startActivity(new Intent(mContext, TakeoutActivity.class));
                } else if (position == 3) {
                    mContext.startActivity(new Intent(mContext, JobActivity.class));
                } else if (position == 4) {
                    mContext.startActivity(new Intent(mContext, HouseActivity.class));
                } else if (position == 5) {
                    mContext.startActivity(new Intent(mContext, TraficActivity.class));
                } else if (position == 6) {
                    mContext.startActivity(new Intent(mContext, ActActivity.class));
                }else if (position == 7) {
                    mContext.startActivity(new Intent(mContext, FeeActivity.class));
                }else if (position == 8) {
                    mContext.startActivity(new Intent(mContext, MovieActivity.class));
                }else if (position == 9) {
//                    ((MainActivity)mContext).gotoFragment();
                    mContext.startActivity(new Intent(mContext, HospitalActivity.class));
//                    ((MainActivity)mContext).getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gridImgArray.length;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView ivPic;
        private TextView tvTitle;
        private LinearLayout ll;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll_grid);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
