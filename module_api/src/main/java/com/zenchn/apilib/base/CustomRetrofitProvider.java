package com.zenchn.apilib.base;

import android.support.annotation.NonNull;

import com.zenchn.apilib.BuildConfig;
import com.zenchn.apilib.retrofit.IRetrofitProvider;
import com.zenchn.apilib.retrofit.interceptor.AddParamsInterceptor;
import com.zenchn.apilib.retrofit.interceptor.TokenHeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author:Hzj
 * @date :2018/3/22/022
 * desc  ：配置OKhttp参数
 * record：
 */

public class CustomRetrofitProvider implements IRetrofitProvider {
    private static String BASE_URL;

    private CustomRetrofitProvider(String baseUrl) {
        BASE_URL = baseUrl;
    }

    static CustomRetrofitProvider getInstance(@NonNull String baseUrl) {
        return new CustomRetrofitProvider(baseUrl);
    }

    @Override
    @NonNull
    public Retrofit getDefaultRetrofit() {
        return CustomRetrofitProvider.SingletonInstance.INSTANCE;
    }

    private static class SingletonInstance {
        private static final Retrofit INSTANCE = create();

        private SingletonInstance() {
        }

        private static Retrofit create() {
            OkHttpClient.Builder builder = (new OkHttpClient.Builder())
                    .connectTimeout(120L, TimeUnit.SECONDS)
                    .readTimeout(120L, TimeUnit.SECONDS)
                    .writeTimeout(120L, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            }

            builder.addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(new TokenHeaderInterceptor())
                    .addNetworkInterceptor(new AddParamsInterceptor());

            OkHttpClient okHttpClient = builder.build();
            return (new Retrofit.Builder())
                    .baseUrl(CustomRetrofitProvider.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(FastJsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    }
}
