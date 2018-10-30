package com.zenchn.support.base;

/**
 * 作    者：wangr on 2017/5/4 11:28
 * 描    述：
 * 修订记录：
 */

public interface ICrashCallback {

    void onCrash(Thread thread, Throwable ex);
}
