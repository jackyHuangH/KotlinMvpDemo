package cn.jacky.kotlindemo.mvp.baseview

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.NonNull
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.app.ApplicationKit
import com.gyf.barlibrary.ImmersionBar
import com.gyf.barlibrary.OnKeyboardListener
import com.zenchn.support.base.AbstractFragment
import com.zenchn.support.kit.AndroidKit
import com.zenchn.support.utils.StringUtils


abstract class BaseFragment : AbstractFragment(), IView {

    protected lateinit var mImmersionBar: ImmersionBar

    /**
     * 视图是否加载完毕
     */
    private var mIsViewPrepare = false

    /**
     * 视图是否第一次展现
     */
    private var mFirstTimeVisible = false

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
    protected open fun isStatusBarEnabled(): Boolean = true

    protected open fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
            .fitsSystemWindows(true)
            .statusBarColor(R.color.color_title_bg)
            .navigationBarColor(R.color.color_title_bg)
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

    protected open fun addOnKeyboardListener(): OnKeyboardListener? = null

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mImmersionBar.init()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsViewPrepare = true
        lazyLoadDataIfPrepared()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && mIsViewPrepare && !mFirstTimeVisible) {
            lazyLoad()
            mFirstTimeVisible = true
        }
    }

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * Viewpager+Fragment 每次对用户可见时，可调用此方法，相当于fragment的resume
     */
    override fun onSupportVisible() {
        //do something
    }

    /**
     * 模拟activity的onBackPressed()事件
     * 方便fragment 调用
     */
    protected fun onFragmentBackPressed() {
        activity?.onBackPressed()
    }

    override fun onPause() {
        activity?.let { AndroidKit.Keyboard.hideSoftInput(it) }
        super.onPause()
    }

    override fun onApiGrantRefuse() {
        ApplicationKit.instance.navigateToLogin(true)
    }


    override fun showMessage(@NonNull msg: CharSequence) {
        if (StringUtils.isNonNull(msg)) {
            super.showMessage(msg)
        }
    }

    override fun onApiFailure(msg: String) {
        showMessage(msg)
    }

    override fun onDestroyView() {
        mImmersionBar.destroy()
        super.onDestroyView()
    }
}
