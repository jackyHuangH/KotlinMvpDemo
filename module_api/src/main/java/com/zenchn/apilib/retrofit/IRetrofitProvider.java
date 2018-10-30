package com.zenchn.apilib.retrofit;


import android.support.annotation.NonNull;

import retrofit2.Retrofit;

/**
 * 作    者：wangr on 2017/7/27 9:01
 * 描    述：
 * 修订记录：
 */
public interface IRetrofitProvider {

    @NonNull
    Retrofit getDefaultRetrofit();

}
