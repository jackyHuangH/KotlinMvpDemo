package cn.jacky.kotlindemo.mvp.search

import cn.jacky.kotlindemo.mvp.basepresenter.IPresenter
import cn.jacky.kotlindemo.mvp.baseview.IView
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/18/018
 * desc  ：
 * record：
 */
interface SearchContract {
    interface View : IView {
        fun showHotSearchWord(hotWords: ArrayList<String>)

        fun showNoResult()

        fun showResultTotalNum(keyword: String, total: Int)

        fun showNewSearchList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean)

        fun showLoadMoreList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean)
    }

    interface Presenter : IPresenter {
        fun getHotSearchWord()

        fun searchRequest(word: String, num: Int)

        fun searchLoadMore()
    }
}