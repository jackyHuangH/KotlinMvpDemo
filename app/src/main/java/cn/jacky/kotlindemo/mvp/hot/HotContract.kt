package cn.jacky.kotlindemo.mvp.hot

import cn.jacky.kotlindemo.mvp.basepresenter.IPresenter
import cn.jacky.kotlindemo.mvp.baseview.IView
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
interface HotContract {
    interface View : IView {
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)
    }

    interface Presenter : IPresenter {
        /**
         * 获取 TabInfo
         */
        fun getTabInfo()
    }
}