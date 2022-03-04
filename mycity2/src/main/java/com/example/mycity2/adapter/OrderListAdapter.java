package com.example.mycity2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycity2.R;
import com.example.mycity2.bean.OrderBean;

import java.util.List;
import java.util.zip.Inflater;

public class OrderListAdapter extends RecyclerView.Adapter{
    private static final String TAG = "OrderListAdapterkkk";
    private Context mContext;
    private List<OrderBean> orderBeans;

    public OrderListAdapter(Context mContext, List<OrderBean> orderBeans) {
        this.mContext = mContext;
        this.orderBeans = orderBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final OrderBean orderBean = orderBeans.get(position);
        viewHolder.llOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:跳转到不同的订单详情页
            }
        });
        viewHolder.tvOrderDate.setText(orderBean.getCreateTime());
        viewHolder.tvOrderId.setText(orderBean.getOrderNo());
        viewHolder.tvOrderState.setText(orderBean.getOrderStatus());
        viewHolder.tvOrderType.setText(orderBean.getOrderTypeName());
    }

    @Override
    public int getItemCount() {
        return orderBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llOrder;
        private TextView tvOrderId;
        private TextView tvOrderDate;
        private TextView tvOrderType;
        private TextView tvOrderState;
        public ViewHolder(@NonNull View view) {
            super(view);
            llOrder = (LinearLayout) view.findViewById(R.id.ll_order);
            tvOrderId = (TextView) view.findViewById(R.id.tv_order_id);
            tvOrderDate = (TextView) view.findViewById(R.id.tv_order_date);
            tvOrderType = (TextView) view.findViewById(R.id.tv_order_type);
            tvOrderState = (TextView) view.findViewById(R.id.tv_order_state);
        }
    }

}
