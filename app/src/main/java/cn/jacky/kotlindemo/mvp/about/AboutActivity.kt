package cn.jacky.kotlindemo.mvp.about

import android.app.Activity
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import com.zenchn.support.router.Router

/**
 * @author:Hzj
 * @date  :2018/12/27/027
 * desc  ：关于
 * record：
 */
class AboutActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_about

    override fun initWidget() {
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