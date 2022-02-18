package com.example.smartcity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartcity.R;
import com.example.smartcity.bean.OrderBean;
import com.example.smartcity.utils.GetRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView orderListView;

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
        orderListView.setAdapter(new OrderAdapter());
        initBean();
    }

    private void initBean() {
        new GetRetrofit(this).getWithToken().getOrderBeanWithoutBody().enqueue(new Callback<OrderBean>() {
            @Override
            public void onResponse(Call<OrderBean> call, Response<OrderBean> response) {
                OrderBean body = response.body();
                int code = body.getCode();
                int total = body.getTotal();
                if (total == 0 && code == 200) {
                    Toast.makeText(OrderActivity.this, "您尚无订单", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderBean> call, Throwable throwable) {
                Toast.makeText(OrderActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class OrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}