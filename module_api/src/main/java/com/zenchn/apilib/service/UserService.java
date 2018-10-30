package com.zenchn.apilib.service;


import com.zenchn.apilib.entity.PortraitEntity;
import com.zenchn.apilib.entity.UserInfoEntity;
import com.zenchn.apilib.model.HttpResultModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 作    者：hzj on 2018/9/18 17:21
 * 描    述：
 * 修订记录：
 */
public interface UserService {

    /**
     * 描    述：
     */
    @GET("account/getAccount/{accessToken}")
    Observable<HttpResultModel<UserInfoEntity>> getUserInfo(@Path("accessToken") String accessToken);


    /**
     * 描    述：
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("account/upload/portrait/{accessToken}")
    Observable<HttpResultModel<PortraitEntity>> updatePortrait(@Path("accessToken") String accessToken,
                                                               @Body RequestBody body);

    /**
     * 修改个人信息
     *
     * @param accessToken
     * @param body
     * @return
     */
    @PUT("account/revInfo/{accessToken}")
    Observable<HttpResultModel<Object>> updateUserInfo(@Path("accessToken") String accessToken,
                                                       @Body RequestBody body);
}
