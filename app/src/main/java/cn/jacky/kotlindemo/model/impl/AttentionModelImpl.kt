package cn.jacky.kotlindemo.model.impl

import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.api.service.OpenEyeService
import cn.jacky.kotlindemo.model.AttentionModel
import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：
 * record：
 */
class AttentionModelImpl : AttentionModel {
    override fun requestAttenionList(callback:RxApiCallback,onSuccess: (HomeBean.Issue) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getFollowInfo()
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

    override fun getMoreIssueList(callback:RxApiCallback,nextPageUrl: String, onSuccess: (HomeBean.Issue) -> Unit) {
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