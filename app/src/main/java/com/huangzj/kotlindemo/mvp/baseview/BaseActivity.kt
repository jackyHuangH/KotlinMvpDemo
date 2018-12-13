package com.huangzj.kotlindemo.mvp.baseview

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import com.gyf.barlibrary.OnKeyboardListener
import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.app.ApplicationKit
import com.zenchn.support.base.AbstractAppCompatActivity
import com.zenchn.support.base.IActivityLifecycle
import com.zenchn.support.base.IUiController
import com.zenchn.support.dafault.DefaultActivityLifecycle
import com.zenchn.support.kit.AndroidKit
import com.zenchn.support.utils.StringUtils
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * @author:Hzj
 * @date  :2018/10/30/030
 * desc  ：
 * record：
 */
abstract class BaseActivity : AbstractAppCompatActivity(), IView ,EasyPermissions.PermissionCallbacks{
    protected val PAGE_SIZE = 10
    protected lateinit var mImmersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstanceState(savedInstanceState)
        initWidget()
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

    public override fun getDefaultUiController(): IUiController {
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
        showResMessage(if (AndroidKit.NetWork.isNetworkAvailable(this)) R.string.common_error_service else R.string.common_error_network)
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
        AndroidKit.Keyboard.hideSoftInput(this)
        super.onPause()
    }

    override fun onDestroy() {
        mImmersionBar.destroy()
        super.onDestroy()
    }

    //---------------权限相关-------------------
    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @io.reactivex.annotations.NonNull permissions: Array<String>, @io.reactivex.annotations.NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("EasyPermissions", "获取成功的权限$perms")
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        }
    }
}