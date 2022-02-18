package com.example.smartcity.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {
    private static final String FILE_NAME = "smart_city";

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp =
                context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp =
                context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }



    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp =
                context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }
}
