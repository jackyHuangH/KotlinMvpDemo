package com.zenchn.support.router;

import android.app.Activity;

/**
 * 作者：wangr on 2016/12/30 11:04
 * 描述：
 */

public interface RouterCallback {

    void onBefore(Activity from, Class<?> to);

    void OnNext(Activity from, Class<?> to);

    void onError(Activity from, Class<?> to, Throwable throwable);

}
