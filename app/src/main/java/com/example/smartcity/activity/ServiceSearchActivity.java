package com.example.smartcity.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.adapter.GridAdapter;
import com.example.smartcity.adapter.RecyclerLinearAdapter;
import com.example.smartcity.adapter.WhichServGridAdapter;
import com.example.smartcity.bean.RowsDTO;
import com.example.smartcity.bean.ServiceTable;
import com.example.smartcity.database.MDBHelper;

import java.sql.SQLException;
import java.util.List;

public class ServiceSearchActivity extends AppCompatActivity {
    private static final String TAG = "ddd";
    private RecyclerView recycler;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_search);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.top_bar_left_back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("搜索结果");
        recycler = findViewById(R.id.recycler);
        Bundle extras = getIntent().getExtras();
        String search = (String)extras.get("title");
        Log.i(TAG, "onCreate: "+search);
        List<ServiceTable> rows = null;
        try {
            rows = MDBHelper.getInstance(this).getServDao().queryBuilder().where().like("servName", "%" + search + "%").query();
            Log.i(TAG, "rows.size() = "+ rows.size()+rows.get(0).toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        recycler.setLayoutManager(new GridLayoutManager(this, 5));
        recycler.setAdapter(new WhichServGridAdapter(rows,this));
    }
}
