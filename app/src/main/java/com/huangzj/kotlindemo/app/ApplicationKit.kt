package com.huangzj.kotlindemo.app

import android.app.Activity
import android.app.NotificationManager
import android.content.Intent
import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.common.Constant
import com.huangzj.kotlindemo.ui.MainActivity
import com.zenchn.apilib.base.ApiManager
import com.zenchn.apilib.util.LoggerKit
import com.zenchn.support.base.AbstractApplicationKit
import com.zenchn.support.base.ActivityLifecycleCallback
import com.zenchn.support.dafault.DefaultActivityLifecycle
import com.zenchn.support.widget.tips.SuperToast


/**
 * @author:Hzj
 * @date  :2018/10/30/030
 * desc  ：
 * record：
 */
class ApplicationKit : AbstractApplicationKit(), ActivityLifecycleCallback {

    private val mLazyActivityLifecycle: DefaultActivityLifecycle by lazy {
        DefaultActivityLifecycle.getInstance()
    }

    fun exitApp() {
        mLazyActivityLifecycle.exitApp()
    }

    fun navigateToLogin(grantRefuse: Boolean) {
        if (grantRefuse) {
            SuperToast.showDefaultMessage(getApplication(), getApplication().getString(R.string.login_error_grant_refused))
        }
        val topActivity = mLazyActivityLifecycle.topActivity
        if (topActivity != null) {
//            LoginActivity
//                    .launch(topActivity)
        } else {
            val intent = Intent(application, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            application.startActivity(intent)
        }
    }

    override fun initSetting() {
        super.initSetting()
        //初始化缓存
        initACache();
        initApiManager();
        LoggerKit.init(Constant.LOGGERKIT_TAG);
        initActivityLifecycle();
        clearNotify();
    }

    private fun initApiManager() {
        ApiManager.init(Constant.BASE_REQUEST_HTTP_HOST)
    }

    /**
     * 初始化本地缓存
     */
    private fun initACache() {
//        try {
//            val aCache = ACache.get(application)
//            ACacheModelImpl.init(aCache)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

    }

    private fun initActivityLifecycle() {
        mLazyActivityLifecycle.addCallback(this)
    }

    /**
     * 清理通知栏
     */
    private fun clearNotify() {
        val nm = application.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancelAll()
    }

    override fun onCrash(thread: Thread?, ex: Throwable?) {
        mLazyActivityLifecycle.exitApp()
    }

    override fun onBackground() {

    }

    override fun onForeground() {

    }

    override fun onDestroyedSelf() {

    }


}