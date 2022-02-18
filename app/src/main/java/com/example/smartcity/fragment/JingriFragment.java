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

public class JingriFragment extends Fragment {
    private static final String TAG = "JingriFragment";
    private RecyclerView recycler;
    private List<RowsDTO> rows;
    public JingriFragment() {
    }


    public static JingriFragment newInstance() {
        JingriFragment fragment = new JingriFragment();
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
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDataFromDB();
//        Call<NewsBean> newsBean = GetRetrofit.get().getNewsBean("9");
//        newsBean.enqueue(new Callback<NewsBean>() {
//            @Override
//            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
//                NewsBean body = response.body();
//                List<RowsDTO> rows = body.getRows();
//                recycler.setAdapter(new RecyclerLinearAdapter(getActivity(),rows));
//            }
//
//            @Override
//            public void onFailure(Call<NewsBean> call, Throwable throwable) {
//                Log.i(TAG, "onFailure: 网络请求出错");
//            }
//        });
    }
    private void getDataFromDB() {
        try {
            rows = MDBHelper.getInstance(getActivity()).getNewsDao().queryBuilder().where().eq("type", "9").query();
            recycler.setAdapter(new RecyclerLinearAdapter(getActivity(),rows));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}