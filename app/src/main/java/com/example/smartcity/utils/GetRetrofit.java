package com.example.smartcity.utils;

import android.content.Context;
import android.util.Log;

import com.example.smartcity.bean.LoginBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {
    private static final String baseUrl = "http://124.93.196.45:10001/";
    private Context mContext;
    public GetRetrofit(Context mContext) {
        this.mContext = mContext;
    }

    private static final String TAG = "GetRetrofit";

    private static RetrofitInterface retrofitInterface;

    public static RetrofitInterface get() {
        if (retrofitInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitInterface = retrofit.create(RetrofitInterface.class);
        }
        return retrofitInterface;
    }

    public static RequestBody getRequestBody(JSONObject jsonObject) {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), String.valueOf(jsonObject));
    }


    public RetrofitInterface getWithToken() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AddTokenInterceptor())
                .addInterceptor(new TokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(RetrofitInterface.class);
    }


    private String getToken() throws IOException, JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://124.93.196.45:10001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String userName = SPUtil.getString(mContext, "userName", "123");
        String password = SPUtil.getString(mContext, "password", "456");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        jsonObject.put("password", password);

        RequestBody body = getRequestBody(jsonObject);
        retrofit2.Response<LoginBean> response = get().getLoginBean(body).execute();
        Log.i(TAG, "getToken: "+response.body().getCode());
        return response.body().getToken();

    }

    /**
     * ???????????????header
     */
    class AddTokenInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            String token = SPUtil.getString(mContext, "token", "abcd");
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", token)
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * Token???????????????????????????token??????????????????????????????????????????????????????token???
     * ???????????????token????????????????????????????????????????????????????????????token?????????
     */
    class TokenInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            // ????????????
            Request oRequest = chain.request();
            // ????????????
            okhttp3.Response response = chain.proceed(oRequest);
            // ??????token?????????
            if (response.body().string().contains("401")) {
                try {
                    String token = getToken(); // ?????????token
                    if (token != null) {
                        SPUtil.putString(mContext, "token", token); // ??????
                        // ???????????????
                        Request newRequest = chain.request().newBuilder()
                                .header("Authorization", token)
                                .build();
                        // ??????????????????
                        return chain.proceed(newRequest);
                    } else {
                        // ???????????????
                        Request newRequest = chain.request().newBuilder()
                                .header("Authorization", "null")
                                .build();
                        // ??????????????????
                        return chain.proceed(newRequest);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return chain.proceed(oRequest);//token????????????????????????????????????
        }
    }
}
