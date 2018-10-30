package com.zenchn.apilib.retrofit;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * @author:Hzj
 * @date :2018/9/12/012
 * desc  ：
 * record：
 */
public final class RetrofitManager implements IRetrofitProvider {
    private IRetrofitProvider mIRetrofitProvider;

    private RetrofitManager() {
    }

    private static class Singleton {
        private static RetrofitManager SINGLEINSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return Singleton.SINGLEINSTANCE;
    }

    /**
     * 配置自定义 retrofit
     * @param retrofitProvider
     * @return
     */
    public RetrofitManager initConfig(IRetrofitProvider retrofitProvider) {
        this.mIRetrofitProvider = retrofitProvider;
        return this;
    }

    /**
     * 使用默认 retrofig 配置，传入baseUrl
     * @param baseUrl
     * @return
     */
    public RetrofitManager initConfig(String baseUrl) {
        this.mIRetrofitProvider = DefRetrofitProvider.getInstance(baseUrl);
        return this;
    }

    @NonNull
    @Override
    public Retrofit getDefaultRetrofit() {
        if (null == this.mIRetrofitProvider) {
            throw new IllegalStateException("you must initConfig first!");
        } else {
            return this.mIRetrofitProvider.getDefaultRetrofit();
        }
    }

    public <T> T create(Class<T> service) {
        return this.getDefaultRetrofit().create(service);
    }
}
