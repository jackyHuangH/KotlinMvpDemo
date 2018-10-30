package com.zenchn.apilib.callback;

/**
 * 作    者：wangr on 2018/3/8 11:14
 * 描    述：
 * 修订记录：
 */

public final class ApiException extends RuntimeException {

    private final String mErrorMsg;

    public ApiException(String message) {
        super(message);
        this.mErrorMsg = message;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.mErrorMsg = message;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }
}
