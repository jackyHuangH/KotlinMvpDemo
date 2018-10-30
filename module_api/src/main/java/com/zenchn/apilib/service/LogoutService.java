package com.zenchn.apilib.service;


import com.zenchn.apilib.model.HttpResultModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 作    者：hzj on 2018/9/17 15:59
 * 描    述：
 * 修订记录：
 */

public interface LogoutService {

    /**
     * 描    述：注销令牌
     */
    @GET("token/logout/{accessToken}")
    Observable<HttpResultModel<Object>> logout(@Path("accessToken") String accessToken);

}
