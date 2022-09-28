package cn.jacky.kotlindemo.mvp.about

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import com.gyf.barlibrary.ImmersionBar
import com.tencent.bugly.beta.Beta
import com.zenchn.support.kit.AndroidKit
import com.zenchn.support.router.Router
import kotlinx.android.synthetic.main.activity_about.*

/**
 * @author:Hzj
 * @date  :2018/12/27/027
 * desc  ：关于
 * record：
 */
class AboutActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_about

    override fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_title_bg)
                .statusBarDarkFont(true, 0.2f)
        mImmersionBar.init()
    }

    @SuppressLint("SetTextI18n")
    override fun initWidget() {
        tv_version_name.text = "v${AndroidKit.Package.getVersionName(this)}"
        //返回
        toolbar.setNavigationOnClickListener { onBackPressed() }
        //检查更新
        fl_check_update.setOnClickListener {
            //bugly检查更新
            Beta.checkAppUpgrade()
        }
    }

    companion object {
        fun launch(from: Activity) {
            Router
                    .newInstance()
                    .from(from)
                    .to(AboutActivity::class.java)
                    .launch()
        }
    }
}