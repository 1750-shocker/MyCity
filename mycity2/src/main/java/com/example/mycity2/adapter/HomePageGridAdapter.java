package com.example.mycity2.adapter;

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

import com.example.mycity2.R;
import com.example.mycity2.activity_service.ActActivity;
import com.example.mycity2.activity_service.BusActivity;
import com.example.mycity2.activity_service.FeeActivity;
import com.example.mycity2.activity_service.HospitalActivity;
import com.example.mycity2.activity_service.HouseActivity;
import com.example.mycity2.activity_service.JobActivity;
import com.example.mycity2.activity_service.MetroActivity;
import com.example.mycity2.activity_service.MovieActivity;
import com.example.mycity2.activity_service.ParkActivity;
import com.example.mycity2.activity_service.TakeoutActivity;
import com.example.mycity2.activity_service.TrafficActivity;
import com.example.mycity2.bean.ServiceTable;
import com.example.mycity2.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class HomePageGridAdapter extends RecyclerView.Adapter{

    private static final String TAG = "gridAdapter";
    private Context mContext;
    //    private int[] gridImgArray = {R.mipmap.park, R.mipmap.metro, R.mipmap.waipai, R.mipmap.jobs,
//            R.mipmap.houses, R.mipmap.car, R.mipmap.activi, R.mipmap.payment, R.mipmap.movies, R.mipmap.data};
//    private String[] gridTitleArray = {"停哪儿", "城市地铁", "外卖订餐", "找工作", "找房子", "智慧交警",
//            "活动管理", "生活交费", "看电影", "更多"};
    List<ServiceTable> rows = new ArrayList<>();
    public HomePageGridAdapter(Context mContext,List<ServiceTable> rows) {
        this.mContext = mContext;
        this.rows = rows;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_home_grid,parent,false);
        return new ItemHolder(v);
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
                    mContext.startActivity(new Intent(mContext, TrafficActivity.class));
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
