package com.zenchn.support.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * 作    者：wangr on 2017/4/27 13:21
 * 描    述：
 * 修订记录：
 */
public interface IActivityLifecycle {

    void onActivityCreated(Activity activity, Bundle savedInstanceState);

    void onActivityStarted(Activity activity);

    void onActivityResumed(Activity activity);

    void onActivityPaused(Activity activity);

    void onActivityStopped(Activity activity);

    void onActivitySaveInstanceState(Activity activity, Bundle outState);

    void onActivityDestroyed(Activity activity);

    Activity getTopActivity();

    void finishAllActivity();

    void exitApp();

}