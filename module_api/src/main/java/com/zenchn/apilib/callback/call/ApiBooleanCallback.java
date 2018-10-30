package com.zenchn.apilib.callback.call;

import android.support.annotation.Nullable;

import com.zenchn.apilib.callback.ApiGrantCallback;
import com.zenchn.apilib.model.HttpResultModel;


/**
 * 作    者：wangr on 2017/6/20 10:33
 * 描    述：
 * 修订记录：
 */

public abstract class ApiBooleanCallback extends HttpCallback<HttpResultModel<Object>> {

    private ApiGrantCallback mGrantCallback;

    public ApiBooleanCallback(ApiGrantCallback mGrantCallback) {
        this.mGrantCallback = mGrantCallback;
    }

    @Override
    protected void onResponseResult(boolean ok, @Nullable HttpResultModel<Object> model, @Nullable String message) {
        onBooleanResult(ok, message);
    }

    @Override
    public void onApiGrantRefuse() {
        if (mGrantCallback != null)
            mGrantCallback.onApiGrantRefuse();
    }

    //只有接口调用失败的时候才有 remark
    public abstract void onBooleanResult(boolean result, String remark);

}
