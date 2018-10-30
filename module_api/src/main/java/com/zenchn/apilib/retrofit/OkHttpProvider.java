package com.zenchn.apilib.retrofit;


import com.zenchn.apilib.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 作    者：wangr on 2017/8/23 10:35
 * 描    述：
 * 修订记录：
 */

public final class OkHttpProvider {

    static OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//错误重连

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();// 增加log拦截器

        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else{
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        return builder.build();
    }
}
