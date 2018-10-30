package com.zenchn.apilib.callback;

/**
 * 作    者：wangr on 2017/8/28 14:53
 * 描    述：
 * 修订记录：
 */

public interface FailureCallback {

    // 请求数据错误
    void onFailure(String err_msg);

}
