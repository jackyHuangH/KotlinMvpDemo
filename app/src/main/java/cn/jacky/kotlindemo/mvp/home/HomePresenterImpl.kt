package cn.jacky.kotlindemo.mvp.home

import cn.jacky.kotlindemo.model.HomeModel
import cn.jacky.kotlindemo.model.impl.HomeModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
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

    //是否是刷新列表
    private var mIsRefresh: Boolean = false

    private lateinit var mNextPageUrl: String
    //记录第一页数据作为banner数据
    private var mHomeBannerBean: HomeBean? = null

    override fun refreshHomeData(num: Int) {
        mIsRefresh = true
        mHomeModelImpl.refreshHomeData(num, this)
    }

    override fun onRefreshHomeListSuccess(homeBean: HomeBean) {
        //记录第一页作为banner数据，请求下一页数据
        mHomeBannerBean = homeBean
        mHomeModelImpl.loadMoreHomeData(homeBean.nextPageUrl, this)
    }

    override fun loadMoreData() {
        mIsRefresh = false
        mHomeModelImpl.loadMoreHomeData(mNextPageUrl, this)
    }

    override fun onGetHomeMoreListSuccess(homeBean: HomeBean) {
        mHomeBannerBean?.let {
            //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
            val bannerItemList = it.issueList[0].itemList
            filterUselessData(bannerItemList)

            //取出banner image 和 title
            var bannerImgs = ArrayList<String>()
            var bannerTitles = ArrayList<String>()
            var bannerCount = it.issueList[0].count
            var bannerList = it.issueList[0].itemList.take(bannerCount)
            bannerList.forEach { item ->
                bannerImgs.add(item.data?.cover?.feed ?: "")
                bannerTitles.add(item.data?.title ?: "")
            }
            mView?.showBannerList(bannerImgs, bannerTitles)
        }
        mNextPageUrl = homeBean.nextPageUrl

        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
        val newItemList = homeBean.issueList[0].itemList
        filterUselessData(newItemList)

        if (mIsRefresh) {
            mView?.setHomeNewData(homeBean.issueList[0].itemList, mNextPageUrl.isNotEmpty())
        } else {
            mView?.setMoreData(homeBean.issueList[0].itemList, mNextPageUrl.isNotEmpty())
        }
    }

    /**
     * //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
     */
    private fun filterUselessData(itemList: ArrayList<HomeBean.Issue.Item>) {
        itemList.filter { item ->
            item.type == "banner2" || item.type == "horizontalScrollCard"
        }.forEach { item ->
            //移除 item
            itemList.remove(item)
        }
    }
}