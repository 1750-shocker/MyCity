package com.example.smartcity.app;

import android.app.Application;
import android.content.Context;

import com.example.smartcity.utils.SPUtil;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }
}
