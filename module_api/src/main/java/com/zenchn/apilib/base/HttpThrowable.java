package com.zenchn.apilib.base;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.zenchn.apilib.callback.ApiFailureCallback;
import com.zenchn.apilib.util.LoggerKit;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 作    者：wangr on 2017/9/10 20:09
 * 描    述：
 * 修订记录：
 */

public class HttpThrowable {

    private static final int UNAUTHORIZED = 401;
//    private static final int FORBIDDEN = 403;
//    private static final int NOT_FOUND = 404;
//    private static final int REQUEST_TIMEOUT = 408;
//    private static final int INTERNAL_SERVER_ERROR = 500;
//    private static final int BAD_GATEWAY = 502;
//    private static final int SERVICE_UNAVAILABLE = 503;
//    private static final int GATEWAY_TIMEOUT = 504;

    public static void handleException(HttpException httpException,@NonNull ApiFailureCallback callback) {
        if (httpException != null) {
            int code = httpException.code();
            if (code == UNAUTHORIZED) {//授权失效
                callback.onApiGrantRefuse();
            } else {//其他网络错误
                String message = null;
                try {
                    Response<?> response = httpException.response();
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        JSONObject jsonObject = null;
                        jsonObject = JSONObject.parseObject(responseBody.string());
                        if (jsonObject != null) {
                            message = jsonObject.getString("message");
                            LoggerKit.e(message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(message))
                    message = "系统升级维护中，请稍后再试！";
                callback.onApiFailure(message);
            }
        }
    }

}
