package cn.jacky.kotlindemo.mvp.hot

import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class HotPresenterImpl(mView: MindeContract.View?) : BasePresenterImpl<MindeContract.View>(mView), MindeContract.Presenter {
}