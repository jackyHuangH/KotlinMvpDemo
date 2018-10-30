package com.zenchn.apilib.callback.rx;


import com.zenchn.apilib.callback.ApiExceptionHandler;
import com.zenchn.apilib.entity.TokenEntity;

/**
 * 作    者：wangr on 2018/8/6 10:35
 * 描    述：
 * 修订记录：
 */

public abstract class RxHttpAuthObserver extends RxHttpDataObserver<TokenEntity> {

    public RxHttpAuthObserver(RxApiCallback rxApiCallback) {
        super(rxApiCallback);
    }

    public RxHttpAuthObserver(RxApiCallback rxApiCallback, String defFailureMsg) {
        super(rxApiCallback, defFailureMsg);
    }

    public RxHttpAuthObserver(RxApiCallback rxApiCallback, String defFailureMsg, String defSuccessMsg) {
        super(rxApiCallback, defFailureMsg, defSuccessMsg);
    }

    @Override
    public void onError(Throwable t) {
        ApiExceptionHandler.handleApiAuthException(t, getDefFailureMsg(), this);
    }

}
