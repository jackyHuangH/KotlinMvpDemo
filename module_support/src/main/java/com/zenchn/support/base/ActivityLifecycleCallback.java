package com.zenchn.support.base;

/**
 * 作    者：wangr on 2017/4/28 9:29
 * 描    述：
 * 修订记录：
 */
public interface ActivityLifecycleCallback {

    void onBackground();

    void onForeground();

    void onDestroyedSelf();
}
