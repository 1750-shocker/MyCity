package com.example.mycity2.fragment_main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycity2.R;
import com.example.mycity2.activity.NewsDetailActivity;
import com.example.mycity2.activity.SearchActivity;
import com.example.mycity2.adapter.HomePageGridAdapter;
import com.example.mycity2.bean.RotationBean;
import com.example.mycity2.bean.ServiceTable;
import com.example.mycity2.util.GetRetrofit;
import com.example.mycity2.util.MDBHelper;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ScaleInTransformer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageFragment extends Fragment {
    private static final String TAG = "HomePageFragmentkkk";
    private EditText etSearch;
    private Banner banner;
    private RecyclerView rvGrid;
    private List<RotationBean.RowsBean> rotations;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_homepage_main, container, false);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        banner = (Banner) view.findViewById(R.id.banner);
        rvGrid = (RecyclerView) view.findViewById(R.id.rv_grid);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        rvGrid.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvGrid.setLayoutManager(new GridLayoutManager(mContext, 5));
        List<ServiceTable> rows = new ArrayList<>();
        try {
            rows = MDBHelper.getInstance(mContext).getServDao().queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        rvGrid.setAdapter(new HomePageGridAdapter(mContext, rows));
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {//关键代码，响应软键盘上的搜索按钮
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = etSearch.getText().toString();
                    etSearch.setText("");
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra("title", search);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        GetRetrofit.get().getRotationBean().enqueue(new Callback<RotationBean>() {
            @Override
            public void onResponse(Call<RotationBean> call, Response<RotationBean> response) {
                rotations = response.body().getRows();
                initBanner();
            }

            @Override
            public void onFailure(Call<RotationBean> call, Throwable throwable) {

            }
        });
    }
    public void initBanner() {
        final BannerImageAdapter<RotationBean.RowsBean> ada = new BannerImageAdapter<RotationBean.RowsBean>(rotations) {

            @Override
            public void onBindView(BannerImageHolder bannerImageHolder, RotationBean.RowsBean rowsBean, int i, int i1) {
                Glide.with(mContext)
                        .load("http://124.93.196.45:10001/" + rowsBean.getAdvImg())
                        .into(bannerImageHolder.imageView);
            }
        };
        banner.setAdapter(ada)
                .addBannerLifecycleObserver(HomePageFragment.this)
                .setPageTransformer(new ScaleInTransformer())
                .setIndicator(new CircleIndicator(mContext))
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object o, int i) {
                        Intent intent = new Intent(mContext, NewsDetailActivity.class);
                        intent.putExtra("id", rotations.get(i).getTargetId());
                        mContext.startActivity(intent);
                    }
                });
    }
}
