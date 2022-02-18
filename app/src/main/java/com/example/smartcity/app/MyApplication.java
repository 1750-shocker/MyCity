package com.example.smartcity.app;

import android.app.Application;
import com.example.smartcity.utils.SPUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SPUtil.putString(this, "netDone", "0");
    }
}
