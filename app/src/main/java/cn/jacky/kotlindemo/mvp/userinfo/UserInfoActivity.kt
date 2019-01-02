package cn.jacky.kotlindemo.mvp.userinfo

import android.app.Activity
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import com.zenchn.support.router.Router

/**
 * @author:Hzj
 * @date  :2019/1/2/002
 * desc  ：个人主页
 * record：
 */
class UserInfoActivity :BaseActivity(){

    override fun getLayoutRes(): Int = R.layout.activity_user_info

    override fun initWidget() {

    }

    companion object {
        fun launch(from: Activity) {
            Router
                    .newInstance()
                    .from(from)
                    .to(UserInfoActivity::class.java)
                    .launch()
        }
    }
}