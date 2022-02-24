package com.example.smartcity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.bean.Chapter;
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

public class AllServGridAdapter extends RecyclerView.Adapter{
    private Chapter chapter;
    private Context mContext;
    private int[] img1 = {R.mipmap.bus, R.mipmap.hospital, R.mipmap.waipai, R.mipmap.houses, R.mipmap.jobs};
    private String[] desc1 = {"智慧巴士", "门诊预约", "外卖订餐", "找房子", "找工作"};
    private int[] img2 = {R.mipmap.metro, R.mipmap.payment, R.mipmap.movies, R.mipmap.activi, R.mipmap.data};
    private String[] desc2 = {"城市地铁", "生活缴费", "看电影", "活动管理", "数据分析"};
    private int[] img3 = {R.mipmap.park, R.mipmap.car};
    private String[] desc3 = {"停哪儿", "智慧交管"};

    public AllServGridAdapter(Chapter chapter, Context mContext) {
        this.chapter = chapter;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        if (chapter.getI() == 1) {
            itemHolder.ivPic.setImageResource(img1[position]);
            itemHolder.tvTitle.setText(desc1[position]);
            itemHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == 0) {
                        mContext.startActivity(new Intent(mContext, BusActivity.class));
                    } else if (position == 1) {
                        mContext.startActivity(new Intent(mContext, HospitalActivity.class));
                    } else if (position == 2) {
                        mContext.startActivity(new Intent(mContext, TakeoutActivity.class));
                    } else if (position == 3) {
                        mContext.startActivity(new Intent(mContext, HouseActivity.class));
                    } else if (position == 4) {
                        mContext.startActivity(new Intent(mContext, JobActivity.class));
                    }
                }
            });
        } else if (chapter.getI() == 2) {
            itemHolder.ivPic.setImageResource(img2[position]);
            itemHolder.tvTitle.setText(desc2[position]);
            itemHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == 0) {
                        mContext.startActivity(new Intent(mContext, MetroActivity.class));
                    } else if (position == 1) {
                        mContext.startActivity(new Intent(mContext, FeeActivity.class));
                    } else if (position == 2) {
                        mContext.startActivity(new Intent(mContext, MovieActivity.class));
                    } else if (position == 3) {
                        mContext.startActivity(new Intent(mContext, ActActivity.class));
                    } else if (position == 4) {
                        mContext.startActivity(new Intent(mContext, ActActivity.class));
                    }
                }
            });
        } else if (chapter.getI() == 3) {
            itemHolder.ivPic.setImageResource(img3[position]);
            itemHolder.tvTitle.setText(desc3[position]);
            itemHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == 0) {
                        mContext.startActivity(new Intent(mContext, ParkActivity.class));
                    } else {
                        mContext.startActivity(new Intent(mContext, TraficActivity.class));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (chapter.getI() == 3) {
            return 2;
        } else {
            return 5;
        }
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
