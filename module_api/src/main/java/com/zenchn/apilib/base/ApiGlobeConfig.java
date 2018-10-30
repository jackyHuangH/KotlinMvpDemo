package com.zenchn.apilib.base;

/**
 * 作    者：wangr on 2018/8/14 15:12
 * 描    述：
 * 修订记录：
 */

public interface ApiGlobeConfig {

    String CLIENT_ID = "00000000000000000000000000000001";
    String CLIENT_SECRET = "zdzc";
    String GRANT_TYPE = "password";
    String REFRESH_TYPE = "refresh_token";

    int MAX_RETRY_COUNT = 3;


    interface ResponseCode {

        int CODE_200 = 200;//请求成功

        int CODE_400 = 400;//错误请求
        int CODE_401 = 401;//未授权
        int CODE_403 = 403;//请求禁止
        int CODE_404 = 404;//未找到页面
        int CODE_405 = 405;//方法禁用
        int CODE_408 = 408;//请求超时

        int CODE_500 = 500;//服务器内部错误
        int CODE_502 = 502;//错误网关
        int CODE_503 = 503;//服务不可用
    }
}
