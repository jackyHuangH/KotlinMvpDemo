package com.zenchn.apilib.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 作    者：wangr on 2017/8/28 14:53
 * 描    述：
 * 修订记录：
 */


public class HttpResultModel<T> {

    @JSONField(name = "success")
    public Boolean success;//True or false  是否成功
    @JSONField(name = "statusCode")
    public Integer statusCode;//成功 默认 1，其他情况另定
    @JSONField(name = "message")
    public String message;
    @JSONField(name = "data")
    public T data;


    @Override
    public String toString() {
        return "HttpResultModel{" +
                "success=" + success +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
