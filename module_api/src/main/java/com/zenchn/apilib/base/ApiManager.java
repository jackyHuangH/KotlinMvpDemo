package com.zenchn.apilib.base;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.zenchn.apilib.model.HttpResultModel;
import com.zenchn.apilib.retrofit.RetrofitManager;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * 作    者：wangr on 2017/8/29 18:18
 * 描    述：
 * 修订记录：
 */

public class ApiManager {
    private static final String API_AUTH_MESSAGE_KEY = "error_description";
    private static final String API_MESSAGE_KEY = "message";
    private static final int API_SUCCESS_STATUS_CODE = 1;//成功 默认 1，其他情况另定

    public static void init(String baseUrl) {
        RetrofitManager.getInstance()
                .initConfig(CustomRetrofitProvider.getInstance(baseUrl));
    }


    /**
     * 判断api是否访问成功
     *
     * @param statusCode
     * @return
     */
    public static boolean isApiSuccess(int statusCode) {
        return API_SUCCESS_STATUS_CODE == statusCode;
    }

    /**
     * 判断api是否访问成功
     *
     * @param httpResultModel
     * @return
     */
    public static boolean isApiSuccess(@NonNull HttpResultModel httpResultModel) {
        return httpResultModel.success != null && httpResultModel.success && isApiSuccess(httpResultModel.statusCode);
    }

    /**
     * 获取api中的应答信息
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Nullable
    public static String obtainApiMessage(@Nullable ResponseBody responseBody) throws IOException {
        String message = null;
        if (responseBody != null) {
            JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
            if (jsonObject != null) {
                message = jsonObject.getString(API_MESSAGE_KEY);
            }
        }
        return message;
    }

    /**
     * 获取api授权接口中的应答信息
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Nullable
    public static String obtainApiAuthMessage(@Nullable ResponseBody responseBody) throws IOException {
        String message = null;
        if (responseBody != null) {
            JSONObject jsonObject = JSONObject.parseObject(responseBody.string());
            if (jsonObject != null) {
                message = jsonObject.getString(API_AUTH_MESSAGE_KEY);
            }
        }
        return message;
    }
}
