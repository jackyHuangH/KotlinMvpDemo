package cn.jacky.kotlindemo.mvp.discovery.attention

import cn.jacky.kotlindemo.model.AttentionModel
import cn.jacky.kotlindemo.model.impl.AttentionModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class AttentionPresenterImpl(mView: AttentionContract.View?) : BasePresenterImpl<AttentionContract.View>(mView), AttentionContract.Presenter, AttentionModel.AttentionListCallback {

    private val mAttentionModelImpl by lazy {
        AttentionModelImpl()
    }

    private lateinit var mNextPageUrl: String

    override fun requestFollowList() {
        mView?.showProgress()
        mAttentionModelImpl.requestAttenionList(this)
    }

    override fun loadMoreData() {
        mAttentionModelImpl.getMoreIssueList(mNextPageUrl, this)
    }

    override fun onRefreshAttentionListSuccess(issue: HomeBean.Issue) {
        mView?.hideProgress()
        mNextPageUrl = issue.nextPageUrl
        mView?.setNewFollowList(issue.itemList, mNextPageUrl.isNotEmpty())
    }

    override fun onGetMoreListSuccess(issue: HomeBean.Issue) {
        mNextPageUrl = issue.nextPageUrl
        mView?.setLoadMoreFollowList(issue.itemList, mNextPageUrl.isNotEmpty())
    }
}