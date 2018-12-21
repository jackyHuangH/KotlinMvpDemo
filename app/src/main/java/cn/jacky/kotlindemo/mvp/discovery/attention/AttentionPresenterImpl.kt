package cn.jacky.kotlindemo.mvp.discovery.attention

import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class AttentionPresenterImpl(mView: AttentionContract.View?) : BasePresenterImpl<AttentionContract.View>(mView), AttentionContract.Presenter {


    override fun requestFollowList() {

    }

    override fun loadMoreData() {
    }
}