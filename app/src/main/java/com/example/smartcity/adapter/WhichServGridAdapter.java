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
import com.example.smartcity.bean.ServiceTable;
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

import java.util.List;

public class WhichServGridAdapter extends RecyclerView.Adapter {
    List<ServiceTable> rows;
    private Context mContext;

    public WhichServGridAdapter(List<ServiceTable> rows, Context mContext) {
        this.rows = rows;
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
        itemHolder.ivPic.setImageResource(rows.get(position).getImgResource());
        itemHolder.tvTitle.setText(rows.get(position).getServName());
        itemHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rows.get(position).getServName().equals("智慧巴士")) {
                    mContext.startActivity(new Intent(mContext, BusActivity.class));
                } else if (rows.get(position).getServName().equals("门诊预约")) {
                    mContext.startActivity(new Intent(mContext, HospitalActivity.class));
                } else if (rows.get(position).getServName().equals("外卖订餐")) {
                    mContext.startActivity(new Intent(mContext, TakeoutActivity.class));
                } else if (rows.get(position).getServName().equals("找房子")) {
                    mContext.startActivity(new Intent(mContext, HouseActivity.class));
                } else if (rows.get(position).getServName().equals("找工作")) {
                    mContext.startActivity(new Intent(mContext, JobActivity.class));
                }else if (rows.get(position).getServName().equals("城市地铁")) {
                    mContext.startActivity(new Intent(mContext, MetroActivity.class));
                }else if (rows.get(position).getServName().equals("生活缴费")) {
                    mContext.startActivity(new Intent(mContext, FeeActivity.class));
                }else if (rows.get(position).getServName().equals("看电影")) {
                    mContext.startActivity(new Intent(mContext, MovieActivity.class));
                }else if (rows.get(position).getServName().equals("活动管理")) {
                    mContext.startActivity(new Intent(mContext, ActActivity.class));
                }else if (rows.get(position).getServName().equals("数据分析")) {
                    mContext.startActivity(new Intent(mContext, ActActivity.class));
                }else if (rows.get(position).getServName().equals("停哪儿")) {
                    mContext.startActivity(new Intent(mContext, ParkActivity.class));
                }else if (rows.get(position).getServName().equals("智慧交管")) {
                    mContext.startActivity(new Intent(mContext, TraficActivity.class));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rows.size();
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
