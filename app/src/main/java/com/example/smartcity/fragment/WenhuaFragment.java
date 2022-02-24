package com.example.smartcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcity.R;
import com.example.smartcity.activity.BannerWebView;
import com.example.smartcity.adapter.RecyclerLinearAdapter;
import com.example.smartcity.bean.BannerBean;
import com.example.smartcity.bean.NewsBean;
import com.example.smartcity.bean.RowsDTO;
import com.example.smartcity.database.MDBHelper;
import com.example.smartcity.utils.GetRetrofit;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WenhuaFragment extends Fragment {
    private static final String TAG = "WenhuaFragment";
    private RecyclerView recycler;
    private List<RowsDTO> rows;
    private Banner banner;

    public static WenhuaFragment newInstance() {
        WenhuaFragment fragment = new WenhuaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jingji, container, false);
        recycler = v.findViewById(R.id.recycler);
        banner = v.findViewById(R.id.news_banner);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Call<NewsBean> newsBean = GetRetrofit.get().getNewsBean("21");
//        newsBean.enqueue(new Callback<NewsBean>() {
//            @Override
//            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
//                NewsBean body = response.body();
//                rows = body.getRows();
//                recycler.setAdapter(new RecyclerLinearAdapter(getActivity(), rows));
//            }
//
//            @Override
//            public void onFailure(Call<NewsBean> call, Throwable throwable) {
//                Log.i(TAG, "onFailure: 网络请求出错");
//            }
//        });
        getDataFromDB();
        initBanner();
    }
    private void initBanner() {
        Context mContext = getActivity();
        List<BannerBean.RowsDTO> list = new ArrayList<>();
        //新建一个含Bean集合，这里为了方便就不动态请求图片地址了，既然api给了图片的地址，那就静态用下
        list.add(new BannerBean.RowsDTO("http://124.93.196.45:10001/prod-api/profile/" +
                "upload/image/2021/05/06/b9d9f081-8a76-41dc-8199-23bcb3a64fcc.png", 28));
        list.add(new BannerBean.RowsDTO("http://124.93.196.45:10001/prod-api/profile/" +
                "upload/image/2021/05/06/e614cb7f-63b0-4cda-bf47-db286ea1b074.png", 29));
        list.add(new BannerBean.RowsDTO("http://124.93.196.45:10001/prod-api/profile/" +
                "upload/image/2021/05/06/242e06f7-9fb0-4e16-b197-206f999c98f2.png", 30));
        BannerImageAdapter<BannerBean.RowsDTO> bannerImageAdapter;
        bannerImageAdapter = new BannerImageAdapter<BannerBean.RowsDTO>(list) {//关键而固定
            @Override
            public void onBindView(BannerImageHolder bannerImageHolder, BannerBean.RowsDTO rowsDTO,
                                   int i, int i1) {
                Glide.with(mContext)
                        .load(rowsDTO.advImg)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .into(bannerImageHolder.imageView);
            }
        };
        banner.setAdapter(bannerImageAdapter).addBannerLifecycleObserver(this).setIndicator(new CircleIndicator(mContext))
                .setOnBannerListener((o, position) -> {
                    Intent intent = new Intent(mContext, BannerWebView.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", list.get(position).targetId);
                    intent.putExtras(bundle);
                    if (mContext != null) {
                        mContext.startActivity(intent);
                    }
                });
    }
    private void getDataFromDB() {
        try {
            rows = MDBHelper.getInstance(getActivity()).getNewsDao().queryBuilder().where().eq("type", "21").query();
            recycler.setAdapter(new RecyclerLinearAdapter(getActivity(),rows));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}