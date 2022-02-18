package com.example.smartcity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcity.R;
import com.example.smartcity.activity.BannerWebView;
import com.example.smartcity.bean.NewsBean;
import com.example.smartcity.bean.RowsDTO;

import java.util.List;

public class RecyclerLinearAdapter extends RecyclerView.Adapter {
    private static final String TAG = "recyclerAdapter";
    private Context mContext;
    private List<RowsDTO> mNewsBeanList;

    public RecyclerLinearAdapter(Context mContext, List<RowsDTO> mNewsBeanList) {
        this.mContext = mContext;
        this.mNewsBeanList = mNewsBeanList;
        Log.i(TAG, "RecyclerLinearAdapter: 实例化成功");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
        Log.i(TAG, "onCreateViewHolder: 适配器1");
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemHolder mHolder = (ItemHolder) holder;
        Glide.with(mContext).load("http://124.93.196.45:10001/" + mNewsBeanList.get(position).getCover()).into(mHolder.newImg);
        mHolder.newTitle.setText(mNewsBeanList.get(position).getTitle());
        mHolder.likeNumber.setText(mNewsBeanList.get(position).getLikeNum() + "");
        mHolder.viewsNumber.setText(mNewsBeanList.get(position).getReadNum() + "");
        mHolder.newDate.setText(mNewsBeanList.get(position).getPublishDate());
        mHolder.newContext.setText(Html.fromHtml(mNewsBeanList.get(position).getContent()).toString());
        mHolder.theroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BannerWebView.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", mNewsBeanList.get(position).getId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        Log.i(TAG, "onBindViewHolder: 适配器2");
    }

    @Override
    public int getItemCount() {
        return mNewsBeanList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView newImg;
        public TextView newTitle;
        public TextView newDate;
        public TextView newContext;
        public TextView likeNumber;
        public TextView viewsNumber;
        private LinearLayout theroot;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            theroot = (LinearLayout) itemView.findViewById(R.id.theroot);
            newImg = (ImageView) itemView.findViewById(R.id.new_img);
            newTitle = (TextView) itemView.findViewById(R.id.new_title);
            newDate = (TextView) itemView.findViewById(R.id.new_date);
            newContext = (TextView) itemView.findViewById(R.id.new_context);
            likeNumber = (TextView) itemView.findViewById(R.id.likeNumber);
            viewsNumber = (TextView) itemView.findViewById(R.id.viewsNumber);
            Log.i(TAG, "ItemHolder: holder搞定");
        }
    }

}
