package com.zenchn.apilib.retrofit;

import android.support.annotation.NonNull;

import com.zenchn.apilib.BuildConfig;
import com.zenchn.apilib.retrofit.converter.FastJsonConverterFactory;
import com.zenchn.apilib.retrofit.interceptor.TokenHeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作    者：wangr on 2017/7/25 17:35
 * 描    述：提供一个默认的线程安全的Retrofit单例
 * 修订记录：
 */
final class DefRetrofitProvider implements IRetrofitProvider {

    private static String BASE_URL;

    private DefRetrofitProvider(String baseUrl) {
        BASE_URL = baseUrl;
    }

    static DefRetrofitProvider getInstance(@NonNull String baseUrl) {
        return new DefRetrofitProvider(baseUrl);
    }

    @NonNull
    @Override
    public Retrofit getDefaultRetrofit() {
        return SingletonInstance.INSTANCE;
    }

    private static class SingletonInstance {

        private static final Retrofit INSTANCE = create();

        private static Retrofit create() {

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);//错误重连

            // 增加log拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            }
            builder.addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(new TokenHeaderInterceptor());
            OkHttpClient okHttpClient = builder.build();

            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
    }

}
