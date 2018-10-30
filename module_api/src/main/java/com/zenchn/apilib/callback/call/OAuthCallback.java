package com.zenchn.apilib.callback.call;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.zenchn.apilib.entity.TokenEntity;
import com.zenchn.apilib.util.LoggerKit;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 作    者：wangr on 2017/8/28 15:11
 * 描    述：
 * 修订记录：
 */
public abstract class OAuthCallback implements Callback<TokenEntity> {

    @Override
    public void onResponse(Call<TokenEntity> call, Response<TokenEntity> response) {
        try {

            int responseCode = response.raw().code();
            if (responseCode == 200 && response.body() != null) {
                onAuthSuccess(response.body());
            } else {
                String errorDescription = null;
                if (response.errorBody() != null) {
                    String errorResponse = response.errorBody().string();
                    if (!TextUtils.isEmpty(errorResponse)) {
                        JSONObject jsonObject = JSONObject.parseObject(errorResponse);
                        errorDescription = jsonObject.getString("error_description");
                    }
                }
                if (TextUtils.isEmpty(errorDescription))
                    errorDescription = "登录失败，请稍候再试！";
                onAuthFailure(errorDescription);
            }

        } catch (Exception e) {
            LoggerKit.e(e);
            onFailure(call, e);
        }
    }

    @Override
    public void onFailure(Call<TokenEntity> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            onAuthFailure("访问超时，请稍后再试！");
        } else if (t instanceof ConnectException) {
            onAuthFailure("网络异常，请检查网络设置！");
        } else {
            onAuthFailure("系统升级维护中，请稍后再试！");
        }
    }


    protected abstract void onAuthSuccess(@NonNull TokenEntity tokenInfo);

    protected abstract void onAuthFailure(@NonNull String error_description);

}
