package cn.jacky.kotlindemo.mvp.classifydetail

import cn.jacky.kotlindemo.model.ClassifyModel
import cn.jacky.kotlindemo.model.impl.ClassifyModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/25/025
 * desc  ：
 * record：
 */
class ClassifyDetailPresenterImpl(mView: ClassifyDetailContract.View) :
        BasePresenterImpl<ClassifyDetailContract.View>(mView), ClassifyDetailContract.Presenter, ClassifyModel.ClassifyDetailListCallback {

    private val mClassifyModelImpl by lazy { ClassifyModelImpl() }

    private lateinit var mNextPageUrl: String

    override fun getCategoryDetailList(id: Long) {
        mClassifyModelImpl.getClassifyDetailList(id, this)
    }

    override fun loadMoreData() {
        mClassifyModelImpl.loadMoreClassifyDetailData(mNextPageUrl, this)
    }

    override fun onRequestDetailListSuccess(issue: HomeBean.Issue) {
        mNextPageUrl = issue.nextPageUrl
        mView?.setCateDetailList(issue.itemList, mNextPageUrl.isNotEmpty())
    }

    override fun onLoadMoreDataSuccess(issue: HomeBean.Issue) {
        mNextPageUrl = issue.nextPageUrl
        mView?.addMoreDetailList(issue.itemList, mNextPageUrl.isNotEmpty())
    }
}