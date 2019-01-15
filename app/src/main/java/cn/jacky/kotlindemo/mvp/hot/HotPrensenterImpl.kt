package cn.jacky.kotlindemo.mvp.hot

import cn.jacky.kotlindemo.model.HotModel
import cn.jacky.kotlindemo.model.impl.HotModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
import cn.jacky.kotlindemo.api.bean.TabInfoBean

/**
 * @author:Hzj
 * @date  :2018/12/26/026
 * desc  ：
 * record：
 */
class HotPrensenterImpl(mView: HotContract.View) : BasePresenterImpl<HotContract.View>(mView), HotContract.Presenter, HotModel.TabInfoCallback {
    private val mHotModelImpl by lazy { HotModelImpl() }

    override fun getTabInfo() {
        mView?.showProgress()
        mHotModelImpl.getTabInfo(this)
    }

    override fun onGetTabInfoSuccess(tabInfo: TabInfoBean) {
        mView?.hideProgress()
        mView?.setTabInfo(tabInfo)
    }
}