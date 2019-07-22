package cn.jacky.kotlindemo.mvp.search

import cn.jacky.kotlindemo.model.impl.SearchModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/18/018
 * desc  ：
 * record：
 */
class SearchPresenterImpl(mView: SearchContract.View?) : BasePresenterImpl<SearchContract.View>(mView),
        SearchContract.Presenter {

    private val mSearchModelImpl by lazy { SearchModelImpl() }
    private lateinit var mNextPageUrl: String
    private var mKeyword: String = ""

    override fun getHotSearchWord() {
        mSearchModelImpl.getHotWordList(this) {
            mView?.showHotSearchWord(it)
        }
    }

    override fun searchRequest(word: String, num: Int) {
        mView?.showProgress()
        mKeyword = word
        mSearchModelImpl.refreshSearchData(word, num, this) { issue ->
            mView?.hideProgress()
            if (issue.count > 0 && issue.itemList.size > 0) {
                mNextPageUrl = issue.nextPageUrl
                mView?.showResultTotalNum(mKeyword, issue.total)
                mView?.showNewSearchList(issue.itemList, mNextPageUrl.isNotEmpty())
            } else {
                mView?.showNoResult()
            }
        }
    }

    override fun searchLoadMore() {
        mSearchModelImpl.loadMoreSearchData(mNextPageUrl, this) { issue ->
            mNextPageUrl = issue.nextPageUrl
            mView?.showLoadMoreList(issue.itemList, mNextPageUrl.isNotEmpty())
        }
    }
}