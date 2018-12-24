package cn.jacky.kotlindemo.mvp.discovery.classify

import cn.jacky.kotlindemo.model.ClassifyModel
import cn.jacky.kotlindemo.model.impl.ClassifyModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
import com.zenchn.apilib.entity.CategoryBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
class ClassifyPresenterImpl(mView: ClassifyContract.View?) : BasePresenterImpl<ClassifyContract.View>(mView), ClassifyContract.Presenter, ClassifyModel.ClassifyListCallback {

    private val mClassifyModelImpl by lazy { ClassifyModelImpl() }

    override fun getClassifyList() {
        mView?.showProgress()
        mClassifyModelImpl.getClassifyList(this)
    }

    override fun onGetClassifyListSuccess(classifyList: List<CategoryBean>) {
        mView?.hideProgress()
        mView?.showClassifyList(classifyList)
    }
}