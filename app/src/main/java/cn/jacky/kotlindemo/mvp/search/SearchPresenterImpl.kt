package cn.jacky.kotlindemo.mvp.search

import cn.jacky.kotlindemo.model.SearchModel
import cn.jacky.kotlindemo.model.impl.SearchModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/18/018
 * desc  ：
 * record：
 */
class SearchPresenterImpl(mView: SearchContract.View?) : BasePresenterImpl<SearchContract.View>(mView), SearchContract.Presenter, SearchModel.HotWordListCallback, SearchModel.SearchListCallback {

    private val mSearchModelImpl by lazy { SearchModelImpl() }
    private lateinit var mNextPageUrl: String
    private var mKeyword: String = ""

    override fun getHotSearchWord() {
        mSearchModelImpl.getHotWordList(this)
    }

    override fun onGetHotWordListSuccess(hotWords: ArrayList<String>) {
        mView?.showHotSearchWord(hotWords)
    }

    override fun searchRequest(word: String, num: Int) {
        mView?.showProgress()
        mKeyword = word
        mSearchModelImpl.refreshSearchData(word, num, this)
    }

    override fun searchLoadMore() {
        mSearchModelImpl.loadMoreSearchData(mNextPageUrl, this)
    }

    override fun onSearchListSuccess(issue: HomeBean.Issue) {
        mView?.hideProgress()
        if (issue.count > 0 && issue.itemList.size > 0) {
            mNextPageUrl = issue.nextPageUrl
            mView?.showResultTotalNum(mKeyword, issue.total)
            mView?.showNewSearchList(issue.itemList, mNextPageUrl.isNotEmpty())
        } else {
            mView?.showNoResult()
        }
    }

    override fun onSearchMoreListSuccess(issue: HomeBean.Issue) {
        mNextPageUrl = issue.nextPageUrl
        mView?.showLoadMoreList(issue.itemList, mNextPageUrl.isNotEmpty())
    }
}