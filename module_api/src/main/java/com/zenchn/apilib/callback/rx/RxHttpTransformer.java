package com.zenchn.apilib.callback.rx;

import com.zenchn.apilib.model.HttpResultModel;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * 作    者：wangr on 2017/9/12 9:45
 * 描    述：
 * 修订记录：
 */

public final class RxHttpTransformer {

    private static class SingletonInstance {

        private static final ObservableTransformer mTransformer = create();

        private static ObservableTransformer<HttpResultModel<Object>, Object> create() {

            return new ObservableTransformer<HttpResultModel<Object>, Object>() {
                @Override
                public ObservableSource<Object> apply(Observable<HttpResultModel<Object>> upstream) {
                    return upstream
                            .map(RxHttpDataProcessor.applyProcessor())
                            .retry(RxHttpRetryProcessor.applyProcessor())
                            .compose(RxSchedulerController.applySchedulers());
                }
            };
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<HttpResultModel<T>, T> applyTransform() {
        return ((ObservableTransformer<HttpResultModel<T>, T>) SingletonInstance.mTransformer);
    }

}
