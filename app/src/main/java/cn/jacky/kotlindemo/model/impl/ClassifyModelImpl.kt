package cn.jacky.kotlindemo.model.impl

import cn.jacky.kotlindemo.api.bean.CategoryBean
import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.api.service.OpenEyeService
import cn.jacky.kotlindemo.model.ClassifyModel
import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/24/024
 * desc  ：
 * record：
 */
class ClassifyModelImpl : ClassifyModel {
    override fun getClassifyList(callback: RxApiCallback, onSuccess: (List<CategoryBean>) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getCategory()
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<List<CategoryBean>>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: List<CategoryBean>?, msg: String?) {
                        if (success) {
                            data?.let { onSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

    override fun getClassifyDetailList(categoryId: Long, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getCategoryDetailList(categoryId)
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean.Issue>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean.Issue?, msg: String?) {
                        if (success) {
                            data?.let { onSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

    override fun loadMoreClassifyDetailData(nextPageUrl: String, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getIssueData(nextPageUrl)
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean.Issue>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean.Issue?, msg: String?) {
                        if (success) {
                            data?.let { onSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

}