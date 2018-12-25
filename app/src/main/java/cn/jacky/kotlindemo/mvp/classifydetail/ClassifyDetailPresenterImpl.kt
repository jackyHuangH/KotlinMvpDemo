package cn.jacky.kotlindemo.mvp.classifydetail

import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/25/025
 * desc  ：
 * record：
 */
class ClassifyDetailPresenterImpl(mView: ClassifyDetailContract.View) :
        BasePresenterImpl<ClassifyDetailContract.View>(mView), ClassifyDetailContract.Presenter {
    override fun getClassifyList() {

    }
}