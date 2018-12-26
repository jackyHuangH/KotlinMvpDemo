package com.zenchn.apilib.retrofit.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:Hzj
 * @date :2018/9/12/012
 * desc  ：OKhttp header 拦截器，在请求头中放入 token
 * 每一个拦截器中，一个关键部分就是使用chain.proceed(request)发起请求。
 * 这个简单的方法就是所有Http工作发生的地方，生成和请求对应的响应
 * record：
 */
public class TokenHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // get token
        String token = "";
        Request originRequest = chain.request();
        //get new request,add header
        Request newRequest = originRequest.newBuilder()
                .addHeader("token", token)
                .build();
        return chain.proceed(newRequest);
    }
}
