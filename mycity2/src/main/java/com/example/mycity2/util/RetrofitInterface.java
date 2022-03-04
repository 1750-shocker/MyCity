package com.example.mycity2.util;

import com.example.mycity2.bean.CodeMsgBean;
import com.example.mycity2.bean.LoginBean;
import com.example.mycity2.bean.NewsListBean;
import com.example.mycity2.bean.OrderListBean;
import com.example.mycity2.bean.RotationBean;
import com.example.mycity2.bean.UserInfoBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @POST("prod-api/api/login")
    Call<LoginBean> getLoginBean(@Body RequestBody body);

    @POST("prod-api/api/register")
    Call<CodeMsgBean> getRegisterBean(@Body RequestBody body);

    @GET("prod-api/press/press/list")
    Call<NewsListBean> getNewsListBean();

    @GET("prod-api/api/common/user/getInfo")
    Call<UserInfoBean> getUserInfoBean();

    @PUT("prod-api/api/common/user")
    Call<CodeMsgBean> putUserBean(@Body RequestBody body);

    @PUT("prod-api/api/common/user/resetPwd")
    Call<CodeMsgBean> putPswBean(@Body RequestBody body);

    @POST("prod-api/api/common/feedback")
    Call<CodeMsgBean> putAdviceBean(@Body RequestBody body);

    @GET("prod-api/api/allorder/list")
    Call<OrderListBean> getOrderListBean();

    @GET("prod-api/api/rotation/list?type=2")
    Call<RotationBean> getRotationBean();

}
