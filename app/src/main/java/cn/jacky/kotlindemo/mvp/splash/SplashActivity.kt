package cn.jacky.kotlindemo.mvp.splash

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.app.ApplicationKit
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.mvp.main.MainActivity
import com.gyf.barlibrary.ImmersionBar
import com.zenchn.support.kit.AndroidKit
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * @author:Hzj
 * @date  :2018/12/13/013
 * desc  ：
 * record：
 */
class SplashActivity : BaseActivity() {

    //供内部类调用context方法，kotlin取消了xxActivity.this
    private val context by lazy { this }

    private val textFontFile = "fonts/Lobster-1.4.otf"
    private val descFontFile = "fonts/FZLanTingHeiS-L-GB-Regular.TTF"

    private var textTypeface: Typeface? = null

    private var descTypeFace: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    init {
        //初始化字体文件
        textTypeface = Typeface.createFromAsset(ApplicationKit.instance.application.assets, textFontFile)
        descTypeFace = Typeface.createFromAsset(ApplicationKit.instance.application.assets, descFontFile)
    }

    override fun initStatusBar() {
        //不适配状态栏
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar.fullScreen(true)
            .init()
    }

    override fun getLayoutRes(): Int = R.layout.activity_splash

    @SuppressLint("SetTextI18n")
    override fun initWidget() {
        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeFace
        val versionName = AndroidKit.Package.getVersionName(this)
        tv_version_name.text = "v$versionName"

        initAlphaAnimation()
        checkPermission()
    }

    private fun initAlphaAnimation() {
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                MainActivity.launch(this@SplashActivity)
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
    }


    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        //需要权限的时候再申请
        if (alphaAnimation != null) {
            iv_web_icon.startAnimation(alphaAnimation)
        }
    }

    override fun onDestroy() {
        textTypeface = null
        descTypeFace = null
        alphaAnimation?.cancel()
        super.onDestroy()
    }
}