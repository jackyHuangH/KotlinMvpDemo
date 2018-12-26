package com.zenchn.apilib.retrofit.interceptor;

import com.zenchn.apilib.util.DeviceUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:Hzj
 * @date :2018/12/26/026
 * desc  ：添加公共参数 拦截器
 * record：
 */
public class AddParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        //添加公共参数
        HttpUrl newUrl = originRequest.url()
                .newBuilder()
                .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                .addQueryParameter("deviceModel", DeviceUtils.getMobileModel())
                .build();
        Request newRequest = originRequest.newBuilder()
                .url(newUrl)
                .build();
        return chain.proceed(newRequest);
    }
}
