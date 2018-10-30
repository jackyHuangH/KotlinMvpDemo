package com.zenchn.support.dafault;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;

import com.zenchn.support.base.ActivityLifecycleCallback;
import com.zenchn.support.base.IActivityLifecycle;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * 作    者：wangr on 2017/4/27 13:26
 * 描    述：管理activity的生命周期
 * 修订记录：
 */
public class DefaultActivityLifecycle implements IActivityLifecycle {

    private Stack<WeakReference<Activity>> activityStack = new Stack<>();//Activity任务栈(由弱引用降低内存泄漏的隐患)
    private Stack<WeakReference<ActivityLifecycleCallback>> mCallbackStack = new Stack<>();//记录所有的观察者
    private int refActivityCount;//记录前台activity数目

    private DefaultActivityLifecycle() {
    }

    private static class SingletonInstance {
        private static final DefaultActivityLifecycle INSTANCE = new DefaultActivityLifecycle();
    }

    public static DefaultActivityLifecycle getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void addCallback(@NonNull ActivityLifecycleCallback mCallback) {
        for (int i = mCallbackStack.size() - 1; i >= 0; i--) {
            if (mCallbackStack.get(i).get() == mCallback)
                return;
        }
        this.mCallbackStack.add(new WeakReference<>(mCallback));
    }

    public void removeCallback(@NonNull ActivityLifecycleCallback mCallback) {
        for (int i = mCallbackStack.size() - 1; i >= 0; i--) {
            if (mCallbackStack.get(i).get() == mCallback)
                this.mCallbackStack.remove(i);
            return;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityStack.add(new WeakReference<>(activity));
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (refActivityCount == 0) {
            for (int i = mCallbackStack.size() - 1; i >= 0; i--) {
                mCallbackStack.get(i).get().onForeground();//处于前台时候的回调
            }
        }
        refActivityCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        refActivityCount--;
        if (refActivityCount == 0) {
            for (int i = mCallbackStack.size() - 1; i >= 0; i--) {
                mCallbackStack.get(i).get().onBackground();//处于后台时候的回调
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (activityStack.get(i).get() == activity) {
                activityStack.remove(i);
                break;
            }
        }
    }


    @Override
    public Activity getTopActivity() {
        if (activityStack.size() > 0)
            return activityStack.peek().get();
        return null;
    }

    @Override
    public void finishAllActivity() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i).get();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    @Override
    public void exitApp() {
        for (int i = mCallbackStack.size() - 1; i >= 0; i--) {
            mCallbackStack.get(i).get().onDestroyedSelf();//杀死自己时候的回调
        }
        finishAllActivity();
        Process.killProcess(Process.myPid());
    }

}
