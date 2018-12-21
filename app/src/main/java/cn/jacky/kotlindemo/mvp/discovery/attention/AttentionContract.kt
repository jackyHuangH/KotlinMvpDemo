package cn.jacky.kotlindemo.mvp.discovery.attention

import cn.jacky.kotlindemo.mvp.basepresenter.IPresenter
import cn.jacky.kotlindemo.mvp.baseview.IView
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
interface AttentionContract {
    interface View : IView {
        /**
         * 设置关注信息新数据
         */
        fun setNewFollowList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean)

        /**
         * 设置关注信息更多数据
         */
        fun setLoadMoreFollowList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean)
    }

    interface Presenter : IPresenter {
        /**
         * 获取List
         */
        fun requestFollowList()

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}