package cn.jacky.kotlindemo.mvp.classifydetail

import cn.jacky.kotlindemo.model.impl.ClassifyModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl

/**
 * @author:Hzj
 * @date  :2018/12/25/025
 * desc  ：
 * record：
 */
class ClassifyDetailPresenterImpl(mView: ClassifyDetailContract.View) :
        BasePresenterImpl<ClassifyDetailContract.View>(mView), ClassifyDetailContract.Presenter {

    private val mClassifyModelImpl by lazy { ClassifyModelImpl() }

    private lateinit var mNextPageUrl: String

    override fun getCategoryDetailList(id: Long) {
        mClassifyModelImpl.getClassifyDetailList(id, this){
            mNextPageUrl = it.nextPageUrl
            mView?.setCateDetailList(it.itemList, mNextPageUrl.isNotEmpty())
        }
    }

    override fun loadMoreData() {
        mClassifyModelImpl.loadMoreClassifyDetailData(mNextPageUrl, this){
            mNextPageUrl = it.nextPageUrl
            mView?.addMoreDetailList(it.itemList, mNextPageUrl.isNotEmpty())
        }
    }
}