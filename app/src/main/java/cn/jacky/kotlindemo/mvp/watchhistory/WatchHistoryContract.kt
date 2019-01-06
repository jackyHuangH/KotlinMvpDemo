package cn.jacky.kotlindemo.mvp.watchhistory

import cn.jacky.kotlindemo.mvp.basepresenter.IPresenter
import cn.jacky.kotlindemo.mvp.baseview.IView
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
interface WatchHistoryContract {

    interface View : IView {

        /**
         * 展示观看记录数据列表
         */
        fun setHomeNewData(itemList: ArrayList<HomeBean.Issue.Item>)

    }

    interface Presenter : IPresenter {

        /**
         * 获取观看记录
         */
        fun getWatchHistory()

    }

}