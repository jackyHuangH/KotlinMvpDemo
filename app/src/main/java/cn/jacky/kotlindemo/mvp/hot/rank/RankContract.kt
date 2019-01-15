package cn.jacky.kotlindemo.mvp.hot.rank

import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.mvp.basepresenter.IPresenter
import cn.jacky.kotlindemo.mvp.baseview.IView

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
interface RankContract {
    interface View : IView {
        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter : IPresenter {
        /**
         * 获取 TabInfo
         */
        fun requestRankList(url: String)
    }
}