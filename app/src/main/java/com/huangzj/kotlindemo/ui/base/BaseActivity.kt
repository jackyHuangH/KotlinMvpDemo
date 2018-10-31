package com.huangzj.kotlindemo.ui.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.view.View
import android.view.WindowManager
import com.gyf.barlibrary.ImmersionBar
import com.gyf.barlibrary.OnKeyboardListener
import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.app.ApplicationKit
import com.zenchn.support.base.AbstractAppCompatActivity
import com.zenchn.support.base.IActivityLifecycle
import com.zenchn.support.base.IUiController
import com.zenchn.support.dafault.DefaultActivityLifecycle
import com.zenchn.support.kit.Android
import com.zenchn.support.utils.StringUtils

/**
 * @author:Hzj
 * @date  :2018/10/30/030
 * desc  ：
 * record：
 */
abstract class BaseActivity : AbstractAppCompatActivity(), BaseView {
    protected val PAGE_SIZE = 10
    protected lateinit var mImmersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initInstanceState(savedInstanceState)
        if (savedInstanceState == null) {
            initWidget()
        }
        initStatusBar()
    }

    //界面布局的初始化操作
    protected fun initInstanceState(savedInstanceState: Bundle?) {
    }

    protected fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .fitsSystemWindows(true)
                //状态栏颜色，不写默认透明色
                .statusBarColor(android.R.color.white)
                //状态栏字体是深色，不写默认为亮色
                .statusBarDarkFont(true, 0.2f)


        //是否需要监听键盘
        if (addOnKeyboardListener() != null) {
            mImmersionBar
                    .keyboardEnable(true)
                    //单独指定软键盘模式
                    .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    .setOnKeyboardListener(addOnKeyboardListener())
        }
        mImmersionBar.init()
    }

    protected fun addOnKeyboardListener(): OnKeyboardListener? {
        return null
    }

    override fun getDefaultUiController(): IUiController {
        return object : CustomUiController(this) {
            override fun getSnackBarParentView(): View {
                return findViewById(getSnackBarParentIdRes())
            }
        }
    }

    @IdRes
    protected fun getSnackBarParentIdRes(): Int {
        return android.R.id.content
    }

    override fun getDefaultActivityLifecycle(): IActivityLifecycle? {
        return DefaultActivityLifecycle.getInstance()
    }

    override fun onApiFailure() {
        showResMessage(if (Android.NetWork.isNetworkAvailable(this)) R.string.common_error_service else R.string.common_error_network)
    }

    override fun onApiGrantRefuse() {
        ApplicationKit.instance.navigateToLogin(true)
    }

    override fun showMessage(@NonNull msg: CharSequence) {
        if (StringUtils.isNonNull(msg)) {
            super.showMessage(msg)
        }
    }

    override fun onPause() {
        Android.Keyboard.hideSoftInput(this)
        super.onPause()
    }

    override fun onDestroy() {
        mImmersionBar.destroy()
        super.onDestroy()
    }
}