package com.example.mycity2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mycity2.R;
import com.example.mycity2.adapter.HomePageGridAdapter;
import com.example.mycity2.bean.ServiceTable;
import com.example.mycity2.util.MDBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceSearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recycler;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search);
        title = getIntent().getStringExtra("title");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        toolbar.setTitle("搜索结果");
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        List<ServiceTable> servBeans = new ArrayList<>();
        try {
            servBeans = MDBHelper.getInstance(this).getServDao().queryBuilder().where().like("servName", "%" + title + "%").query();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        recycler.setLayoutManager(new GridLayoutManager(this, 5));
        recycler.setAdapter(new HomePageGridAdapter(this, servBeans));
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