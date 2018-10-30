package com.zenchn.apilib.callback.rx;

/**
 * 作    者：wangr on 2018/8/6 10:35
 * 描    述：
 * 修订记录：
 */

public class RxHttpSilentObserver extends RxHttpResultObserver {

    public RxHttpSilentObserver() {
        super(null);
    }

    public RxHttpSilentObserver(String defFailureMsg) {
        super(null, defFailureMsg);
    }

    public RxHttpSilentObserver(String defFailureMsg, String defSuccessMsg) {
        super(null, defFailureMsg, defSuccessMsg);
    }

    protected void onHttpResponseResult(boolean success, String msg) {

    }

    @Override
    protected void onHttpBooleanResult(boolean success, String msg) {

    }
}
