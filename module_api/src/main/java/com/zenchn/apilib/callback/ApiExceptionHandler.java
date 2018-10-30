package com.zenchn.apilib.callback;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zenchn.apilib.base.ApiGlobeConfig;
import com.zenchn.apilib.base.ApiManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;


/**
 * 作    者：wangr on 2018/3/8 13:06
 * 描    述：api访问异常处理类
 * 修订记录：
 */

public final class ApiExceptionHandler {

    static IApiExceptionReporter mReporter;

    /**
     * 处理重试
     *
     * @param retryCount
     * @param throwable
     * @return
     */
    public static boolean handleApiRetry(@NonNull Integer retryCount, @NonNull Throwable throwable) {
        if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).code();
            if (ApiGlobeConfig.ResponseCode.CODE_400 == code || ApiGlobeConfig.ResponseCode.CODE_401 == code) {
                return false;
            }
        }
        return !(throwable instanceof ApiException) && retryCount <= ApiGlobeConfig.MAX_RETRY_COUNT;
    }

    /**
     * 处理异常
     *
     * @param throwable
     * @param defaultErrorMsg
     * @param callback
     */
    public static void handleApiAuthException(@NonNull Throwable throwable, @NonNull String defaultErrorMsg, ApiFailureCallback callback) {
        if (callback != null) {
            if (throwable instanceof HttpException && ApiGlobeConfig.ResponseCode.CODE_401 == ((HttpException) throwable).code()) {//授权失效
                callback.onApiGrantRefuse();
            } else {
                String errMessage = obtainApiAuthExceptionMessage(throwable, defaultErrorMsg);
                callback.onApiFailure(errMessage);
            }
        }
    }

    /**
     * 提取异常中包含的错误信息
     *
     * @param throwable
     * @param defaultErrorMsg
     * @return
     */
    private static String obtainApiAuthExceptionMessage(@NonNull Throwable throwable, @NonNull String defaultErrorMsg) {
        //网络错误
        if (throwable instanceof HttpException) {

            Response<?> response = ((HttpException) throwable).response();
            ResponseBody errorResponseBody = response.errorBody();

            String httpErrorMessage = null;
            try {
                if (errorResponseBody != null) {
                    httpErrorMessage = ApiManager.obtainApiAuthMessage(errorResponseBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(httpErrorMessage)) {
                httpErrorMessage = defaultErrorMsg;
            }
            return httpErrorMessage;
        } else {
            return obtainApiConnectExceptionMessage(throwable, defaultErrorMsg);
        }
    }

    /**
     * 提取普通接口异常中包含的错误信息
     *
     * @param throwable
     * @param defaultErrorMsg
     * @return
     */
    private static String obtainApiExceptionMessage(@NonNull Throwable throwable, @NonNull String defaultErrorMsg) {
        if (throwable instanceof HttpException) {
            Response<?> response = ((HttpException) throwable).response();
            ResponseBody responseBody = response.errorBody();
            String httpErrorMessage = null;
            try {
                httpErrorMessage = ApiManager.obtainApiMessage(responseBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(httpErrorMessage)) {
                httpErrorMessage = defaultErrorMsg;
            }
            return httpErrorMessage;
        } else {
            return obtainApiConnectExceptionMessage(throwable, defaultErrorMsg);
        }
    }

    /**
     * 提取接口连接错误信息
     *
     * @param throwable
     * @param defErrorMsg
     * @return
     */
    private static String obtainApiConnectExceptionMessage(@NonNull Throwable throwable, @NonNull String defErrorMsg) {
        String connectErrorMessage;
        if (throwable instanceof ApiException) {
            connectErrorMessage = ((ApiException) throwable).getErrorMsg();
        } else if (throwable instanceof ConnectException) {
            connectErrorMessage = "连接错误，请检查您的网络！";
        } else if (throwable instanceof SocketTimeoutException) {
            connectErrorMessage = "访问超时，请稍后再试！";
        } else {
            connectErrorMessage = defErrorMsg;
        }
        if (TextUtils.isEmpty(connectErrorMessage)) {
            connectErrorMessage = "系统升级维护中，请稍后再试！!";
        }
        return connectErrorMessage;
    }

    /**
     * 处理异常
     *
     * @param throwable
     * @param defaultErrorMsg
     * @param callback
     */
    public static void handleException(@NonNull Throwable throwable, @NonNull String defaultErrorMsg, ApiFailureCallback callback) {
        if (callback != null) {
            if (throwable instanceof HttpException && ApiGlobeConfig.ResponseCode.CODE_401 == ((HttpException) throwable).code()) {
                //授权失效
                callback.onApiGrantRefuse();
            } else {
                String errMessage = obtainApiExceptionMessage(throwable, defaultErrorMsg);
                if (mReporter != null) {
                    mReporter.report(throwable);
                }
                callback.onApiFailure(errMessage);
            }
        }
    }
}
