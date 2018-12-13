package com.huangzj.kotlindemo.mvp.home

import com.huangzj.kotlindemo.model.HomeModel
import com.huangzj.kotlindemo.model.impl.HomeModelImpl
import com.huangzj.kotlindemo.mvp.basepresenter.BasePresenterImpl
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class HomePresenterImpl(mView: HomeContract.View?) : BasePresenterImpl<HomeContract.View>(mView), HomeContract.Presenter, HomeModel.HomeListCallback {

    private val mHomeModelImpl: HomeModelImpl by lazy {
        HomeModelImpl()
    }

    private var mNextPageUrl: String? = ""
    //记录第一页数据作为banner数据
    private var mHomeBannerBean: HomeBean? = null

    override fun requestHomeData(num: Int) {
        mHomeModelImpl.refreshHomeData(num, this)
    }

    override fun loadMoreData() {
        mNextPageUrl?.let { mHomeModelImpl.loadMoreHomeData(it, this) }
    }

    override fun onGetHomeListSuccess(homeBean: HomeBean) {
        if (mHomeBannerBean == null) {
            //记录第一页作为banner数据，请求下一页数据
            mHomeBannerBean = homeBean
            mNextPageUrl = homeBean.nextPageUrl
            loadMoreData()
        } else {
            mView?.setHomeData(homeBean, homeBean.nextPageUrl.isEmpty())
        }

    }

    override fun onGetHomeMoreListSuccess(homeBean: HomeBean) {
        mHomeBannerBean?.let { mView?.showBannerList(it) }
        mView?.setMoreData(homeBean.issueList[0].itemList, homeBean.nextPageUrl.isEmpty())
    }
}