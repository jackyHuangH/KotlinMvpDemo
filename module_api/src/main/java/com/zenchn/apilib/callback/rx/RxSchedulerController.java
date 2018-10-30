package com.zenchn.apilib.callback.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 作    者：wangr on 2018/6/14 14:04
 * 描    述：
 * 修订记录：
 */

public final class RxSchedulerController {

    private static class SingletonInstance {

        private static final ObservableTransformer mTransformer = create();

        private static ObservableTransformer create() {

            return new ObservableTransformer() {

                @Override
                public ObservableSource apply(@NonNull Observable upstream) {
                    return upstream
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) SingletonInstance.mTransformer;
    }

}

