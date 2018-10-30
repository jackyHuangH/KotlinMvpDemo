package com.zenchn.apilib.callback.rx;

import com.zenchn.apilib.callback.ApiFailureCallback;

import io.reactivex.disposables.Disposable;

/**
 * 作    者：wangr on 2018/6/1 14:48
 * 描    述：
 * 修订记录：
 */

public interface RxApiCallback extends ApiFailureCallback {

    /**
     * rxjava 订阅时调用
     *
     * @param disposable
     */
    void onRegisterObserver(Disposable disposable);

}
