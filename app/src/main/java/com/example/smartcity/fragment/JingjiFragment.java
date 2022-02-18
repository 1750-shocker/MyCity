package com.example.smartcity.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcity.MainActivity;
import com.example.smartcity.R;
import com.example.smartcity.adapter.RecyclerLinearAdapter;
import com.example.smartcity.bean.NewsBean;
import com.example.smartcity.bean.RowsDTO;
import com.example.smartcity.database.MDBHelper;
import com.example.smartcity.utils.GetRetrofit;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
新闻列表的fragment都是只有一个RecyclerView
 */
public class JingjiFragment extends Fragment {
    private static final String TAG = "JingjiFragment";
    private RecyclerView recycler;
    private List<RowsDTO> rows;

    public static JingjiFragment newInstance() {
        JingjiFragment fragment = new JingjiFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jingji, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    /*
    关键代码，在该方法中进行网络请求，并初始化适配器
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getDataFromNet();
        getDataFromDB();
//        recycler.setAdapter(new RecyclerLinearAdapter(getActivity(),rows));
    }

    private void getDataFromDB() {
        try {
            rows = MDBHelper.getInstance(getActivity()).getNewsDao().queryBuilder().where().eq("type", "20").query();
            recycler.setAdapter(new RecyclerLinearAdapter(getActivity(),rows));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void getDataFromNet() {
        Call<NewsBean> newsBean = GetRetrofit.get().getNewsBean("20");//关键代码，网络层
        newsBean.enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                NewsBean body = response.body();
                rows = body.getRows();//网络层返回下来取含Bean集合，传参也是用
                // 含Bean集合，之后会把网络层抽出来，初始化适配器与数据库关联，数据库在特定条件下与网络层交互
                recycler.setAdapter(new RecyclerLinearAdapter(getActivity(),rows));
            }

            @Override
            public void onFailure(Call<NewsBean> call, Throwable throwable) {
                Log.i(TAG, "onFailure: 网络请求出错");
            }
        });
    }
}