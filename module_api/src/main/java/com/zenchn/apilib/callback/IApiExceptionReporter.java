package com.zenchn.apilib.callback;

import android.support.annotation.NonNull;

/**
 * 作    者：wangr on 2018/9/7 16:24
 * 描    述：
 * 修订记录：
 */

public interface IApiExceptionReporter {

    void report(@NonNull Throwable throwable);

}
