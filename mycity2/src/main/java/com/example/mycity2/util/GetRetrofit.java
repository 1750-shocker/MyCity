package com.example.mycity2.util;

import android.content.Context;
import android.util.Log;

import com.example.mycity2.bean.LoginBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {
    private static final String TAG = "GetRetrofitkkk";
    private static final String baseUrl = "http://124.93.196.45:10001/";
    private Context mContext;

    public GetRetrofit(Context mContext) {
        this.mContext = mContext;
    }

    private static RetrofitInterface mretrofitInterface;

    public static RetrofitInterface get() {
        if (mretrofitInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mretrofitInterface = retrofit.create(RetrofitInterface.class);
        }
        return mretrofitInterface;
    }

    public static RequestBody getRequestBody(JSONObject jsonObject) {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), String.valueOf(jsonObject));
    }


    public RetrofitInterface getWithToken() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AddTokenInterceptor())
                .build();
        Log.i(TAG, "getWithToken: 加了拦截器");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(RetrofitInterface.class);
    }

    /**
     * 为请求增加header
     */
    class AddTokenInterceptor implements Interceptor {

        private Request newRequest;

        @Override
        public Response intercept(Chain chain) throws IOException {
            try {
                String ntoken = getToken(); // 获得新token
                SPUtil.putString(mContext, "token", ntoken); // 保存
                // 构建新请求
                newRequest = chain.request().newBuilder()
                        .header("Authorization", ntoken)
                        .build();
                return chain.proceed(newRequest);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return chain.proceed(newRequest);
        }
    }

    private String getToken() throws IOException, JSONException {
        String username = SPUtil.getString(mContext, "username", "123");
        String password = SPUtil.getString(mContext, "password", "456");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        RequestBody body = getRequestBody(jsonObject);
        retrofit2.Response<LoginBean> response = get().getLoginBean(body).execute();
        Log.i(TAG, "getToken: " + response.body().getCode());
        return response.body().getToken();

    }
}
