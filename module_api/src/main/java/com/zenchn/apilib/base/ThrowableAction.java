package com.zenchn.apilib.base;


import com.zenchn.apilib.callback.ApiFailureCallback;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * 作    者：wangr on 2017/9/10 19:57
 * 描    述：
 * 修订记录：
 */

public abstract class ThrowableAction implements Consumer<Throwable>, ApiFailureCallback {

    @Override
    public final void accept(Throwable throwable) {
        if (throwable instanceof CustomThrowable) {
            onResponseMessage(throwable.getMessage());
        } else if (throwable instanceof ConnectException) {
            onResponseMessage("连接超时，请稍后再试！");
        } else if (throwable instanceof SocketTimeoutException) {
            onResponseMessage("访问超时，请稍后再试！");
        } else if (throwable instanceof HttpException) {
            HttpThrowable.handleException((HttpException) throwable, this);
        } else {
            onResponseThrowable(throwable);
        }
    }

    @Override
    public void onApiFailure(String err_msg) {
        onResponseMessage(err_msg);
    }

    protected abstract void onResponseThrowable(Throwable throwable);

    protected abstract void onResponseMessage(String message);


}
