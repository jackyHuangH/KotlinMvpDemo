package com.zenchn.apilib.callback;

/**
 * 作    者：wangr on 2017/8/29 17:16
 * 描    述：
 * 修订记录：
 */
public interface ApiFailureCallback extends ApiGrantCallback {

    /**
     * 请求数据错误
     */
    void onApiFailure(String err_msg);

}
