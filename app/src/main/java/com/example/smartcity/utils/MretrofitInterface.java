package com.example.smartcity.utils;

import com.example.smartcity.bean.CommonBean;
import com.example.smartcity.bean.LoginBean;
import com.example.smartcity.bean.NewBean;
import com.example.smartcity.bean.NewsBean;
import com.example.smartcity.bean.OrderBean;
import com.example.smartcity.bean.UserBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MretrofitInterface {
    @POST("prod-api/api/login")
    Call<LoginBean> getLoginBean(@Body RequestBody body);

    @POST("prod-api/api/register")
    Call<CommonBean> getCommonBean(@Body RequestBody body);

    @GET("prod-api/press/press/list")
    Call<NewsBean> getNewsBean(@Query("type") String type);

    @GET("prod-api/press/press/list")
    Call<NewsBean> getNewsBean();

    @GET("prod-api/press/press/{id}")
    Call<NewBean> getNewBean(@Path("id") int id);

    @GET("prod-api/api/common/user/getInfo")
    Call<UserBean> getUserBean();

    @PUT("prod-api/api/common/user")
    Call<CommonBean> putUserBean(@Body RequestBody body);

    @PUT("prod-api/api/common/user/resetPwd")
    Call<CommonBean> putPswBean(@Body RequestBody body);

    @POST("prod-api/api/common/feedback")
    Call<CommonBean> putAdviceBean(@Body RequestBody body);

    @GET("prod-api/api/allorder/list")
    Call<OrderBean> getOrderBean(@Body RequestBody body);
    @GET("prod-api/api/allorder/list")
    Call<OrderBean> getOrderBeanWithoutBody();
}
