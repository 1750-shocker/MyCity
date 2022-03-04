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
                .addInterceptor(new TokenInterceptor())
                .build();

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

        @Override
        public Response intercept(Chain chain) throws IOException {
            String token = SPUtil.getString(mContext, "token", "abcd");
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", token)
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * Token拦截器，验证当前的token是否有效，如果无效则发起请求获得新的token，
     * 然后将新的token更新请求，并重新发送到服务器。同时将新的token保存。
     */
    class TokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            // 获得请求
            Request oRequest = chain.request();
            // 发送请求
            Response response = chain.proceed(oRequest);
            // 判断token有效性
            if (response.body().string().contains("401")) {
                try {
                    String token = getToken(); // 获得新token
                    if (token != null) {
                        SPUtil.putString(mContext, "token", token); // 保存
                        // 构建新请求
                        Request newRequest = chain.request().newBuilder()
                                .header("Authorization", token)
                                .build();
                        // 再次发送请求
                        return chain.proceed(newRequest);
                    } else {
                        // 构建新请求
                        Request newRequest = chain.request().newBuilder()
                                .header("Authorization", "null")
                                .build();
                        // 再次发送请求
                        return chain.proceed(newRequest);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return chain.proceed(oRequest);//token有效，则发送原来的请求。
        }
        private String getToken() throws IOException, JSONException {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://124.93.196.45:10001/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
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
}
