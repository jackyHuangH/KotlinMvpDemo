package com.huangzj.kotlindemo.mvp.home

import com.huangzj.kotlindemo.mvp.basepresenter.IPresenter
import com.huangzj.kotlindemo.mvp.baseview.IView
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
interface HomeContract {

    interface View : IView {

        /**
         * 展示banner
         */
        fun showBannerList(homeBean: HomeBean)

        /**
         * 设置第一次请求的数据
         */
        fun setHomeData(homeBean: HomeBean, hasNextPage: Boolean)

        /**
         * 设置加载更多的数据
         */
        fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean)
    }

    interface Presenter : IPresenter {

        /**
         * 获取首页精选数据
         */
        fun requestHomeData(num: Int)

        /**
         * 加载更多数据
         */
        fun loadMoreData()

    }

}