package cn.jacky.kotlindemo.mvp.discovery.attention

import cn.jacky.kotlindemo.model.impl.AttentionModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class AttentionPresenterImpl(mView: AttentionContract.View?) : BasePresenterImpl<AttentionContract.View>(mView), AttentionContract.Presenter {

    private val mAttentionModelImpl by lazy {
        AttentionModelImpl()
    }

    private lateinit var mNextPageUrl: String

    override fun requestFollowList() {
        mView?.showProgress()
        mAttentionModelImpl.requestAttenionList(this) {
            mView?.hideProgress()
            mNextPageUrl = it.nextPageUrl
            mView?.setNewFollowList(it.itemList, mNextPageUrl.isNotEmpty())
        }
    }

    override fun loadMoreData() {
        mAttentionModelImpl.getMoreIssueList(this, mNextPageUrl) {
            mNextPageUrl = it.nextPageUrl
            mView?.setLoadMoreFollowList(it.itemList, mNextPageUrl.isNotEmpty())
        }
    }
}