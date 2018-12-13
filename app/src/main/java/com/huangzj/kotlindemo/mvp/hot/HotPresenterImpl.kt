package com.huangzj.kotlindemo.mvp.hot

import com.huangzj.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class HotPresenterImpl(mView: MindeContract.View?) : BasePresenterImpl<MindeContract.View>(mView), MindeContract.Presenter {
}