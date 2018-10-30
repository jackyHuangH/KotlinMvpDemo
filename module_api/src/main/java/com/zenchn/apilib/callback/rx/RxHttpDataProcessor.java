package com.zenchn.apilib.callback.rx;

import com.zenchn.apilib.base.ApiManager;
import com.zenchn.apilib.callback.ApiException;
import com.zenchn.apilib.model.HttpResultModel;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 作    者：wangr on 2018/6/14 14:04
 * 描    述：
 * 修订记录：
 */

public final class RxHttpDataProcessor {

    private static class SingletonInstance {

        private static final Object EMPTY_DATA = new Object();
        private static final Function INSTANCE = create();

        private static Function<HttpResultModel<Object>, Object> create() {

            return new Function<HttpResultModel<Object>, Object>() {

                @Override
                public Object apply(@NonNull HttpResultModel<Object> tHttpResultModel) throws Exception {
                    if (tHttpResultModel != null) {
                        //访问成功(有数据或者有结果无数据)
                        if (ApiManager.isApiSuccess(tHttpResultModel)) {
                            Object data = tHttpResultModel.data;
                            return data != null ? data : EMPTY_DATA;
                        } else {
                            //访问成功(无数据)
                            throw new ApiException(tHttpResultModel.message);
                        }
                    } else {
                        //访问失败
                        throw new ApiException("访问失败：接口无数据!");
                    }
                }
            };
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Function<HttpResultModel<T>, T> applyProcessor() {
        return (Function<HttpResultModel<T>, T>) SingletonInstance.INSTANCE;
    }

}

