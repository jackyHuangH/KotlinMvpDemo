package com.zenchn.apilib.service;


import com.zenchn.apilib.entity.TokenEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 作    者：hzj on 2018/9/17 15:15
 * 描    述：
 * 修订记录：
 */

public interface LoginService {

    /**
     * 作    者：hzj on 2018/9/17 15:15
     * 描    述：授权接口
     */
    @FormUrlEncoded
    @POST("token/login")
    Observable<TokenEntity> login(@Field("client_id") String client_id,
                                  @Field("client_secret") String client_secret,
                                  @Field("grant_type") String grant_type,
                                  @Field("username") String username,
                                  @Field("password") String password,
                                  @Field("device_id") String device_id,
                                  @Field("device_name") String device_name,
                                  @Field("device_type") String device_type);

    /**
     * 作    者：hzj on 2018/9/17 15:15
     * 描    述：刷新令牌接口
     */
    @FormUrlEncoded
    @POST("token/login")
    Observable<TokenEntity> refreshToken(@Field("client_id") String client_id,
                                   @Field("client_secret") String client_secret,
                                   @Field("grant_type") String grant_type,
                                   @Field("refresh_token") String refresh_token);

    @GET("normal")
    Observable<String> test();
}
