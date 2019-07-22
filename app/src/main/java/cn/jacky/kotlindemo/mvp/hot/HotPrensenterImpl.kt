package cn.jacky.kotlindemo.mvp.hot

import cn.jacky.kotlindemo.model.impl.HotModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/26/026
 * desc  ：
 * record：
 */
class HotPrensenterImpl(mView: HotContract.View) : BasePresenterImpl<HotContract.View>(mView), HotContract.Presenter {

    private val mHotModelImpl by lazy { HotModelImpl() }

    override fun getTabInfo() {
        mView?.showProgress()
        mHotModelImpl.getTabInfo(this) {
            mView?.hideProgress()
            mView?.setTabInfo(it)
        }
    }
}