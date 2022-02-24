package com.example.smartcity.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.bean.Chapter;

import java.util.List;

public class ChapterAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Chapter> mData;

    public ChapterAdapter(Context mContext, List<Chapter> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ParentHolder parentViewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_group, viewGroup, false);
            parentViewHolder = new ParentHolder();
            parentViewHolder.textView = view.findViewById(R.id.tv_name);
            view.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentHolder) view.getTag();
        }
        parentViewHolder.textView.setText(mData.get(i).getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_child, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.mRecycler = view.findViewById(R.id.recycler);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        GridLayoutManager m = new GridLayoutManager(mContext, 5);
        childViewHolder.mRecycler.setLayoutManager(m);
        childViewHolder.mRecycler.setAdapter(new AllServGridAdapter(mData.get(i), mContext));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public static class ParentHolder {
        TextView textView;
    }

    public static class ChildViewHolder {
        RecyclerView mRecycler;
    }
}
