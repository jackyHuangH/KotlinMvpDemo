package cn.jacky.kotlindemo.mvp.watchhistory

import android.app.Activity
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import com.zenchn.support.router.Router

/**
 * @author:Hzj
 * @date  :2019/1/2/002
 * desc  ：观看历史
 * record：
 */
class WatchHistoryActivity :BaseActivity(){

    override fun getLayoutRes(): Int = R.layout.activity_user_info

    override fun initWidget() {
    }

    companion object {
        fun launch(from: Activity) {
            Router
                    .newInstance()
                    .from(from)
                    .to(WatchHistoryActivity::class.java)
                    .launch()
        }
    }
}