package cn.jacky.kotlindemo.mvp.discovery.classify

import cn.jacky.kotlindemo.model.impl.ClassifyModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class ClassifyPresenterImpl(mView: ClassifyContract.View?) : BasePresenterImpl<ClassifyContract.View>(mView), ClassifyContract.Presenter{

    private val mClassifyModelImpl by lazy { ClassifyModelImpl() }

    override fun getClassifyList() {
        mView?.showProgress()
        mClassifyModelImpl.getClassifyList(this){
            mView?.hideProgress()
            mView?.showClassifyList(it)
        }
    }
}