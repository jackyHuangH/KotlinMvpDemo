package com.zenchn.apilib.callback;

/**
 * 作    者：wangr on 2017/2/21 16:28
 * 描    述：Api基本的回调
 * 修订记录：
 */

public interface ApiCallback<T> extends FailureCallback, ApiGrantCallback {

    // 请求数据成功
    void onSuccess(T data);

}
