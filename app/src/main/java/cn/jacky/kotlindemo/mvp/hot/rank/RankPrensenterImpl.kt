package cn.jacky.kotlindemo.mvp.hot.rank

import cn.jacky.kotlindemo.model.impl.HotModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/26/026
 * desc  ：
 * record：
 */
class RankPrensenterImpl(mView: RankContract.View) : BasePresenterImpl<RankContract.View>(mView), RankContract.Presenter {

    private val mHotModelImpl by lazy { HotModelImpl() }

    override fun requestRankList(apiUrl: String) {
        mView?.showProgress()
        mHotModelImpl.requestRankList(apiUrl, this) {
            mView?.hideProgress()
            mView?.setRankList(it.itemList)
        }
    }
}