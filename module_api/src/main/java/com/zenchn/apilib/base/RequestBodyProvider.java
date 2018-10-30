package com.zenchn.apilib.base;

import com.alibaba.fastjson.JSON;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作    者：wangr on 2017/8/28 17:43
 * 描    述：
 * 修订记录：
 */


public class RequestBodyProvider {

    /**
     * 创建一个请求体
     *
     * @param jsonString
     * @return
     */
    public static RequestBody createRequestBody(String jsonString) {
        return RequestBody.create(MediaType.parse("application/json"), jsonString);
    }

    /**
     * 创建一个请求体
     *
     * @param object
     * @return
     */
    public static RequestBody createRequestBody(Object object) {
        return RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(object));
    }


}
