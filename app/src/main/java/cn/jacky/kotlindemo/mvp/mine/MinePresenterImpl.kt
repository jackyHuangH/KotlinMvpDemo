package com.jacky.kotlindemo.mvp.hot

import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
import cn.jacky.kotlindemo.mvp.hot.MindeContract

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class MinePresenterImpl(mView: MindeContract.View?) : BasePresenterImpl<MindeContract.View>(mView), MindeContract.Presenter {
}