package com.example.smartcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.smartcity.R;
import com.example.smartcity.activity.NewsSearchActivity;
import com.example.smartcity.activity.ServiceSearchActivity;
import com.example.smartcity.adapter.ChapterAdapter;
import com.example.smartcity.bean.Chapter;
import com.example.smartcity.bean.ServiceTable;
import com.example.smartcity.database.MDBHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ServicesFragment extends Fragment {
    private List<Chapter> mData = new ArrayList<>();
    private Context mContext;
    private ExpandableListView expandableListView;
    private EditText edtSearch;
    public ServicesFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        expandableListView = view.findViewById(R.id.expand_list);
        edtSearch = (EditText) view.findViewById(R.id.edt_search);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mData.add(new Chapter("便民服务", 1));
        mData.add(new Chapter("生活服务", 2));
        mData.add(new Chapter("车主服务", 3));
        expandableListView.setAdapter(new ChapterAdapter(mContext, mData));
        MDBHelper m = MDBHelper.getInstance(mContext);
        Dao<ServiceTable, Integer> servDao = m.getServDao();
        List<ServiceTable> rows = new ArrayList<>();
        rows.add(new ServiceTable("智慧巴士", R.mipmap.bus));
        rows.add(new ServiceTable("门诊预约", R.mipmap.hospital));
        rows.add(new ServiceTable("外卖订餐", R.mipmap.waipai));
        rows.add(new ServiceTable("找房子", R.mipmap.houses));
        rows.add(new ServiceTable("找工作", R.mipmap.jobs));
        rows.add(new ServiceTable("城市地铁", R.mipmap.metro));
        rows.add(new ServiceTable("生活缴费", R.mipmap.payment));
        rows.add(new ServiceTable("看电影", R.mipmap.movies));
        rows.add(new ServiceTable("活动管理", R.mipmap.activi));
        rows.add(new ServiceTable("数据分析", R.mipmap.data));
        rows.add(new ServiceTable("停哪儿", R.mipmap.park));
        rows.add(new ServiceTable("智慧交管", R.mipmap.car));
        try {
            servDao.queryRaw("delete from ServiceTable");
            servDao.queryRaw("update sqlite_sequence SET seq = 0 where name ='ServiceTable'");
            servDao.create(rows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        initSearch();
    }
    private void initSearch() {
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {//关键代码，响应软键盘上的搜索按钮
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = edtSearch.getText().toString();
                    Intent intent = new Intent(mContext, ServiceSearchActivity.class);
                    intent.putExtra("title", search);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}