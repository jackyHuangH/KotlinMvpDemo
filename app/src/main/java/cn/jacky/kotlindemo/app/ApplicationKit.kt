package cn.jacky.kotlindemo.app

import android.app.Activity
import android.app.NotificationManager
import android.content.Intent
import cn.jacky.kotlindemo.BuildConfig
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.common.Constant
import cn.jacky.kotlindemo.mvp.main.MainActivity
import com.umeng.commonsdk.UMConfigure
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
class ApplicationKit private constructor() : AbstractApplicationKit(), ActivityLifecycleCallback {

    object Const {
        const val UMENG_APP_KEY = "5c32f9a7f1f5568bf5000eeb"
    }

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
        //初始化友盟
        initUMeng()
        initApiManager()
        LoggerKit.init(Constant.LOGGERKIT_TAG)
        initActivityLifecycle()
        clearNotify()
    }

    private fun initApiManager() {
        ApiManager.init(Constant.BASE_REQUEST_HTTP_HOST)
    }

    /**
     * 初始化友盟
     */
    private fun initUMeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(application, UMConfigure.DEVICE_TYPE_PHONE, Const.UMENG_APP_KEY)

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
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


    private object SingletonInstance {
        internal val INSTANCE = ApplicationKit()
    }

    //伴生类，对象为静态单例
    companion object {
        val instance: ApplicationKit
            get() = SingletonInstance.INSTANCE
    }

}