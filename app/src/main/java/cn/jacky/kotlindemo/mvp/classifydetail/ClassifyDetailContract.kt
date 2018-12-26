package cn.jacky.kotlindemo.mvp.classifydetail

import cn.jacky.kotlindemo.mvp.basepresenter.IPresenter
import cn.jacky.kotlindemo.mvp.baseview.IView
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
interface ClassifyDetailContract {
    interface View : IView {
        /**
         *  设置列表数据
         */
        fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean)

        /**
         *  添加列表数据
         */
        fun addMoreDetailList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean)
    }

    interface Presenter : IPresenter {
        fun getCategoryDetailList(id: Long)

        fun loadMoreData()
    }
}