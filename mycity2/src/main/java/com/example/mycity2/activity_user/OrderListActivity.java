package com.example.mycity2.activity_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mycity2.R;
import com.example.mycity2.adapter.OrderListAdapter;
import com.example.mycity2.bean.OrderBean;
import com.example.mycity2.bean.OrderListBean;
import com.example.mycity2.util.GetRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recycler;
    List<OrderBean> orderBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
        setView();
    }

    private void setView() {
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        toolbar.setTitle("全部订单");
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        new GetRetrofit(this).getWithToken().getOrderListBean().enqueue(new Callback<OrderListBean>() {
            @Override
            public void onResponse(Call<OrderListBean> call, Response<OrderListBean> response) {
                final OrderListBean body = response.body();
                if (body.getCode() == 200) {
                    orderBeans = body.getRows();
                    recycler.setAdapter(new OrderListAdapter(OrderListActivity.this, orderBeans));
                } else {
                    Toast.makeText(OrderListActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrderListBean> call, Throwable throwable) {
                Toast.makeText(OrderListActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}