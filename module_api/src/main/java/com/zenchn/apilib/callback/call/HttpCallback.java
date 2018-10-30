package com.zenchn.apilib.callback.call;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.zenchn.apilib.base.HttpThrowable;
import com.zenchn.apilib.callback.ApiFailureCallback;
import com.zenchn.apilib.callback.ApiGrantCallback;
import com.zenchn.apilib.model.HttpResultModel;
import com.zenchn.apilib.util.LoggerKit;

import java.net.ConnectException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/8/28 14:53
 * 描    述：
 * 修订记录：
 */

public abstract class HttpCallback<T extends HttpResultModel<? extends Object>> implements Callback<T>, ApiGrantCallback, ApiFailureCallback {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            int responseCode = response.raw().code();
            T body = response.body();
            if (responseCode == 200 && body != null) {//访问成功
                if (body.data != null) {
                    LoggerKit.d(body.data.toString());
                }
                onResponseResult((body.success && (body.statusCode == 1)), body, body.message);
            } else if (response.code() == 401) {//令牌失效
                onApiGrantRefuse();
            } else {//访问错误
                String message = null;
                ResponseBody responseBody = response.errorBody();
                if (responseBody != null) {
                    JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
                    if (jsonObject != null) {
                        message = jsonObject.getString("message");
                        LoggerKit.e(message);
                    }
                }
                if (TextUtils.isEmpty(message)) {
                    message = "系统升级维护中，请稍后再试！";
                }
                onResponseResult(false, null, message);
            }

        } catch (Exception e) {
            LoggerKit.e(e);
            onFailure(call, e);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {//网络问题会走该回调
        if (t instanceof HttpException) {
            HttpThrowable.handleException((HttpException) t, this);
        } else if (t instanceof ConnectException) {
            onApiFailure("网络异常，请检查网络设置！");
        } else {
            onResponseResult(false, null, "系统升级维护中，请稍后再试！");
        }
    }

    @Override
    public void onApiFailure(String err_msg) {
        onResponseResult(false, null, err_msg);
    }

    protected abstract void onResponseResult(boolean ok, T model, @Nullable String message);

}
