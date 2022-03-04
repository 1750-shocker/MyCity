package com.example.mycity2.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycity2.R;
import com.example.mycity2.activity.NewsDetailActivity;
import com.example.mycity2.bean.RowsBean;

import java.util.List;
import java.util.zip.Inflater;

public class NewsListAdapter extends RecyclerView.Adapter{
    private static final String TAG = "NewsListAdapterkkk";
    private Context mContext;
    List<RowsBean> rows;

    public NewsListAdapter(Context mContext, List<RowsBean> rows) {
        this.mContext = mContext;
        this.rows = rows;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final RowsBean rowsBean = rows.get(position);
        Glide.with(mContext).load("http://124.93.196.45:10001/" + rowsBean.getCover()).into(viewHolder.newsImg);
        viewHolder.newsContent.setText(Html.fromHtml(rowsBean.getContent()));
        viewHolder.newsDate.setText(rowsBean.getPublishDate());
        viewHolder.newsLike.setText(rowsBean.getLikeNum()+"");
        viewHolder.newsSeen.setText(rowsBean.getReadNum()+"");
        viewHolder.newsTitle.setText(rowsBean.getTitle());
        viewHolder.newsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("id", rowsBean.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout newsLl;
        private ImageView newsImg;
        private TextView newsTitle;
        private TextView newsContent;
        private TextView newsDate;
        private TextView newsLike;
        private TextView newsSeen;

        public ViewHolder(@NonNull View view) {
            super(view);
            newsLl = (LinearLayout) view.findViewById(R.id.news_ll);
            newsImg = (ImageView) view.findViewById(R.id.news_img);
            newsTitle = (TextView) view.findViewById(R.id.news_title);
            newsContent = (TextView) view.findViewById(R.id.news_content);
            newsDate = (TextView) view.findViewById(R.id.news_date);
            newsLike = (TextView) view.findViewById(R.id.news_like);
            newsSeen = (TextView) view.findViewById(R.id.news_seen);

        }
    }

}
