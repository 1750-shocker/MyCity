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
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycity2.R;
import com.example.mycity2.activity.ServiceSearchActivity;
import com.example.mycity2.adapter.ChapterAdapter;
import com.example.mycity2.bean.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceFragment extends Fragment {
    private static final String TAG = "ServiceFragmentkkk";
    private EditText etSearch;
    private ExpandableListView expandList;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_service_main, container, false);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        expandList = (ExpandableListView) view.findViewById(R.id.expand_list);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        List<Chapter> mData = new ArrayList<>();
        mData.add(new Chapter("便民服务", 1));
        mData.add(new Chapter("生活服务", 2));
        mData.add(new Chapter("车主服务", 3));
        expandList.setAdapter(new ChapterAdapter(mContext, mData));
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {//关键代码，响应软键盘上的搜索按钮
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = etSearch.getText().toString();
                    etSearch.setText("");
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
