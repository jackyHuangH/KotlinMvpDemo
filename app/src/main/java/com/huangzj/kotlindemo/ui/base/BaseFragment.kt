package com.huangzj.kotlindemo.ui.base

import android.os.Bundle
import android.support.annotation.NonNull
import android.view.WindowManager
import com.gyf.barlibrary.ImmersionBar
import com.gyf.barlibrary.OnKeyboardListener
import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.app.ApplicationKit
import com.zenchn.support.base.AbstractFragment
import com.zenchn.support.kit.AndroidKit
import com.zenchn.support.utils.StringUtils

abstract class BaseFragment : AbstractFragment(), BaseView {

    protected lateinit var mImmersionBar: ImmersionBar


    override fun initInstanceState(savedInstanceState: Bundle?) {
        super.initInstanceState(savedInstanceState)
        //如果要在Fragment单独使用沉浸式，请在onSupportVisible实现沉浸式
        if (isStatusBarEnabled()) {
            initStatusBar()
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected fun isStatusBarEnabled(): Boolean {
        return true
    }

    protected fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .fitsSystemWindows(true)
                .statusBarColor(android.R.color.white)
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


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mImmersionBar.init()
        }
    }

    /**
     * 模拟activity的onBackPressed()事件
     * 方便fragment 调用
     */
    protected fun onFragmentBackPressed() {
        activity?.onBackPressed()
    }

    override fun onApiGrantRefuse() {
        ApplicationKit.instance.navigateToLogin(true)
    }


    override fun showMessage(@NonNull msg: CharSequence) {
        if (StringUtils.isNonNull(msg)) {
            super.showMessage(msg)
        }
    }

    override fun onApiFailure() {
        showResMessage(if (AndroidKit.NetWork.isNetworkAvailable(activity!!)) R.string.common_error_service else R.string.common_error_network)
    }

    override fun onDestroyView() {
        mImmersionBar.destroy()
        super.onDestroyView()
    }
}
