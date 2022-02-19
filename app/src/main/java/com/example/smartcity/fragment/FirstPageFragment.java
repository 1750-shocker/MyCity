package com.example.smartcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcity.R;
import com.example.smartcity.activity.BannerWebView;
import com.example.smartcity.activity.NewsSearchActivity;
import com.example.smartcity.adapter.GridAdapter;
import com.example.smartcity.bean.BannerBean;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


public class FirstPageFragment extends Fragment {
    private EditText edtSearch;
    private Banner banner;
    private Context mContext;
    private List<BannerBean.RowsDTO> list = new ArrayList<>();
    private BannerImageAdapter<BannerBean.RowsDTO> bannerImageAdapter;
    private RecyclerView recyclerView;

    public FirstPageFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);
        edtSearch = view.findViewById(R.id.edt_search);
        banner = view.findViewById(R.id.banner);
        recyclerView = view.findViewById(R.id.rv_grid);
        GridLayoutManager m = new GridLayoutManager(mContext, 5);
        recyclerView.setLayoutManager(m);
        recyclerView.setAdapter(new GridAdapter(mContext));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBanner();
        initSearch();
    }

    private void initSearch() {
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {//关键代码，响应软键盘上的搜索按钮
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = edtSearch.getText().toString();
                    Intent intent = new Intent(mContext, NewsSearchActivity.class);
                    intent.putExtra("title", search);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    private void initBanner() {
        //新建一个含Bean集合，这里为了方便就不动态请求图片地址了，既然api给了图片的地址，那就静态用下
        list.add(new BannerBean.RowsDTO("http://124.93.196.45:10001/prod-api/profile/" +
                "upload/image/2021/05/06/b9d9f081-8a76-41dc-8199-23bcb3a64fcc.png", 28));
        list.add(new BannerBean.RowsDTO("http://124.93.196.45:10001/prod-api/profile/" +
                "upload/image/2021/05/06/e614cb7f-63b0-4cda-bf47-db286ea1b074.png", 29));
        list.add(new BannerBean.RowsDTO("http://124.93.196.45:10001/prod-api/profile/" +
                "upload/image/2021/05/06/242e06f7-9fb0-4e16-b197-206f999c98f2.png", 30));
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
}