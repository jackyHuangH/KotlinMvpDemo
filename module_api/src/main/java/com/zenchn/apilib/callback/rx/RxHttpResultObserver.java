package com.zenchn.apilib.callback.rx;

/**
 * 作    者：wangr on 2018/8/6 10:35
 * 描    述：rxjava 无数据 boolean结果处理
 * 修订记录：
 */

public abstract class RxHttpResultObserver extends RxHttpDataObserver<Object> {

    public RxHttpResultObserver(RxApiCallback rxApiCallback) {
        super(rxApiCallback);
    }

    public RxHttpResultObserver(RxApiCallback rxApiCallback, String defFailureMsg) {
        super(rxApiCallback, defFailureMsg);
    }

    public RxHttpResultObserver(RxApiCallback rxApiCallback, String defFailureMsg, String defSuccessMsg) {
        super(rxApiCallback, defFailureMsg, defSuccessMsg);
    }

    @Override
    protected void onHttpResponseResult(boolean success, Object data, String msg) {
        onHttpBooleanResult(success, msg);
    }

    protected abstract void onHttpBooleanResult(boolean success, String msg);
}
