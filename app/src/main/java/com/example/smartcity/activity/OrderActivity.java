package com.example.smartcity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcity.R;
import com.example.smartcity.bean.OrderBean;
import com.example.smartcity.utils.GetRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "ORDER-ACTIVITY";
    private Toolbar toolbar;
    private ListView orderListView;
    private List<OrderBean.RowsDTO> rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        orderListView = (ListView) findViewById(R.id.order_listView);
        toolbar.setTitle("全部订单");
        toolbar.setNavigationIcon(R.drawable.top_bar_left_back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initBean();

    }

    private void initBean() {
        new GetRetrofit(this).getWithToken().getOrderBeanWithoutBody().enqueue(new Callback<OrderBean>() {
            @Override
            public void onResponse(Call<OrderBean> call, Response<OrderBean> response) {
                OrderBean body = response.body();
                int code = body.getCode();
                int total = body.getTotal();
                rows = body.getRows();
                Log.i(TAG, "onResponse: 请求成功，code = " + code);
                if (total == 0 && code == 200) {
                    Toast.makeText(OrderActivity.this, "您尚无订单", Toast.LENGTH_SHORT).show();
                } else if(code!=200){
                    Toast.makeText(OrderActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
                orderListView.setAdapter(new OrderAdapter());
            }

            @Override
            public void onFailure(Call<OrderBean> call, Throwable throwable) {
                Log.i(TAG, "onFailure: 请求失败");
            }
        });
    }

    private class OrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return rows.size();
        }

        @Override
        public Object getItem(int i) {
            return rows.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(OrderActivity.this).inflate(R.layout.order_item, null);
                viewHolder.orderNo = (TextView) convertView.findViewById(R.id.order_No);
                viewHolder.orderDate = (TextView) convertView.findViewById(R.id.order_date);
                viewHolder.orderType = (TextView) convertView.findViewById(R.id.order_type);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.orderNo.setText(rows.get(i).getOrderNo());
            if (rows.get(i).getPayTime() == null) {
                viewHolder.orderDate.setText("待支付");
            } else {
                viewHolder.orderDate.setText(rows.get(i).getPayTime());
            }
            viewHolder.orderType.setText(rows.get(i).getOrderTypeName());
            return convertView;
        }

        public final class ViewHolder {
            TextView orderNo;
            TextView orderDate;
            TextView orderType;
        }
    }
}