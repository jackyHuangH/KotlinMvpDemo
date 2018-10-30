package com.zenchn.apilib.callback.rx;

import com.zenchn.apilib.callback.ApiExceptionHandler;
import com.zenchn.apilib.callback.ApiFailureCallback;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 作    者：wangr on 2018/8/6 10:35
 * 描    述：rxjava 有数据 结果处理
 * 修订记录：
 */

public abstract class RxHttpDataObserver<T> implements Observer<T>, ApiFailureCallback {

    protected final RxApiCallback mRxApiCallback;
    private String mDefFailureMsg;
    private String mDefSuccessMsg;

    public RxHttpDataObserver(RxApiCallback rxApiCallback) {
        this.mRxApiCallback = rxApiCallback;
    }

    public RxHttpDataObserver(RxApiCallback rxApiCallback, String defFailureMsg) {
        this.mRxApiCallback = rxApiCallback;
        this.mDefFailureMsg = defFailureMsg;
    }

    public RxHttpDataObserver(RxApiCallback rxApiCallback, String defFailureMsg, String defSuccessMsg) {
        this.mRxApiCallback = rxApiCallback;
        this.mDefFailureMsg = defFailureMsg;
        this.mDefSuccessMsg = defSuccessMsg;
    }

    protected String getDefFailureMsg() {
        return mDefFailureMsg;
    }

    protected String getDefSuccessMsg() {
        return mDefSuccessMsg;
    }

    /**
     * 注册时绑定presenter
     *
     * @param disposable
     */
    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        if (mRxApiCallback != null) {
            mRxApiCallback.onRegisterObserver(disposable);
        }
    }

    @Override
    public void onNext(T t) {
        onHttpResponseResult(true, t, getDefSuccessMsg());
    }

    @Override
    public void onError(Throwable t) {
        ApiExceptionHandler.handleException(t, getDefFailureMsg(), this);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onApiGrantRefuse() {
        if (mRxApiCallback != null) {
            mRxApiCallback.onApiGrantRefuse();
        }
    }

    @Override
    public void onApiFailure(String errMsg) {
        onHttpResponseResult(false, null, errMsg);
    }

    protected abstract void onHttpResponseResult(boolean success, T data, String msg);

}
