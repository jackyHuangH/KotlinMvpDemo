package com.zenchn.apilib.callback.rx;

import com.zenchn.apilib.callback.ApiExceptionHandler;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiPredicate;

/**
 * 作    者：wangr on 2017/9/12 9:45
 * 描    述：
 * 修订记录：
 */

public class RxHttpRetryProcessor {

    private static class SingletonInstance {

        private static final BiPredicate<? super Integer, ? super Throwable> INSTANCE = create();

        private static BiPredicate<? super Integer, ? super Throwable> create() {

            return new BiPredicate<Integer, Throwable>() {
                @Override
                public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) {
                    return ApiExceptionHandler.handleApiRetry(integer, throwable);
                }
            };
        }

    }

    public static BiPredicate<? super Integer, ? super Throwable> applyProcessor() {
        return SingletonInstance.INSTANCE;
    }

}
