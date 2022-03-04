package com.example.mycity2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mycity2.R;
import com.example.mycity2.activity.NewsDetailActivity;
import com.example.mycity2.adapter.NewsListAdapter;
import com.example.mycity2.bean.RowsBean;
import com.example.mycity2.util.MDBHelper;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.transformer.RotateDownPageTransformer;
import com.youth.banner.transformer.ScaleInTransformer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment {
    private static final String TAG = "NewsListFragmentkkk";
    int i;
    private RecyclerView recycler;
    private Banner newsListBanner;
    private List<RowsBean> rows;

    public static NewsListFragment newInstance(int i) {
        final NewsListFragment fragment = new NewsListFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt("int", i);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        i = getArguments().getInt("int", 0);
        final View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        newsListBanner = (Banner) view.findViewById(R.id.news_list_banner);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if (i == 0) {
                rows = MDBHelper.getInstance(getActivity()).getRowsDao().queryBuilder().where().eq("type", "9").query();
            } else if (i == 1) {
                rows = MDBHelper.getInstance(getActivity()).getRowsDao().queryBuilder().where().eq("type", "17").query();
            } else if (i == 2) {
                rows = MDBHelper.getInstance(getActivity()).getRowsDao().queryBuilder().where().eq("type", "19").query();
            } else if (i == 3) {
                rows = MDBHelper.getInstance(getActivity()).getRowsDao().queryBuilder().where().eq("type", "20").query();
            } else if (i == 4) {
                rows = MDBHelper.getInstance(getActivity()).getRowsDao().queryBuilder().where().eq("type", "21").query();
            } else if (i == 5) {
                rows = MDBHelper.getInstance(getActivity()).getRowsDao().queryBuilder().where().eq("type", "22").query();
            }
            recycler.setAdapter(new NewsListAdapter(getActivity(), rows));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initBanner();
    }

    private void initBanner() {
        Context mContext = getActivity();
        List<RowsBean> bannerData = new ArrayList<>();
        bannerData.add(rows.get(rows.size() - 1));
        bannerData.add(rows.get(rows.size() - 2));
        bannerData.add(rows.get(rows.size() - 3));
        String[] titles = {rows.get(rows.size() - 1).getTitle(), rows.get(rows.size() - 2).getTitle(), rows.get(rows.size() - 3).getTitle()};
        final BannerImageAdapter<RowsBean> bannerImageAdapter = new BannerImageAdapter<RowsBean>(bannerData) {
            @Override
            public void onBindView(BannerImageHolder bannerImageHolder, RowsBean rowsBean, int i, int i1) {
                Glide.with(mContext).load("http://124.93.196.45:10001/" + rowsBean.getCover())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .into(bannerImageHolder.imageView);
            }
        };
        newsListBanner.setAdapter(bannerImageAdapter)
                .addBannerLifecycleObserver(this)
                .setPageTransformer(new ScaleInTransformer())
                .setIndicator(new CircleIndicator(mContext))
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object o, int i) {
                        final Intent intent = new Intent(mContext, NewsDetailActivity.class);
                        intent.putExtra("id", bannerData.get(i).getId());
                        mContext.startActivity(intent);
                    }
                });
    }
}
