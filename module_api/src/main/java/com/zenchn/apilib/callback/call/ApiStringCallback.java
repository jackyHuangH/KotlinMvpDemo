package com.zenchn.apilib.callback.call;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zenchn.apilib.base.HttpThrowable;
import com.zenchn.apilib.callback.ApiFailureCallback;
import com.zenchn.apilib.callback.ApiGrantCallback;
import com.zenchn.apilib.util.LoggerKit;

import java.net.ConnectException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/8/28 14:53
 * 描    述：返回原始String类型数据
 * 修订记录：
 */

public abstract class ApiStringCallback implements Callback<String>, ApiGrantCallback, ApiFailureCallback {

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        try {
            int responseCode = response.raw().code();
            String body = response.body();
            JSONObject jsonResult = (JSONObject) JSON.parse(body);
            String message = jsonResult.getString("message");
            boolean success = jsonResult.getBoolean("success");
            int statusCode = jsonResult.getInteger("statusCode");//成功为1

            if (responseCode == 200 && (null != body && body.length() > 0)) {//访问成功
                LoggerKit.d(body);
                onResponseResult((success && (statusCode == 1)), body, message);
            } else if (response.code() == 401) {//令牌失效
                onApiGrantRefuse();
            } else {//访问错误
                String errorMessage = null;
                ResponseBody responseBody = response.errorBody();
                if (responseBody != null) {
                    JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
                    if (jsonObject != null) {
                        errorMessage = jsonObject.getString("message");
                        LoggerKit.e(errorMessage);
                    }
                }
                if (TextUtils.isEmpty(errorMessage)) {
                    errorMessage = "系统升级维护中，请稍后再试！";
                }
                onResponseResult(false, null, errorMessage);
            }

        } catch (Exception e) {
            LoggerKit.e(e);
            onFailure(call, e);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {//网络问题会走该回调
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

    protected abstract void onResponseResult(boolean ok, String responseString, @Nullable String message);

}
