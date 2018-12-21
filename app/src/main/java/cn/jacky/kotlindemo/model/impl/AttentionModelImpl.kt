package cn.jacky.kotlindemo.model.impl

import cn.jacky.kotlindemo.api.service.OpenEyeService
import cn.jacky.kotlindemo.model.AttentionModel
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.entity.HomeBean
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：
 * record：
 */
class AttentionModelImpl : AttentionModel {

    override fun requestAttenionList(callback: AttentionModel.AttentionListCallback) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getFollowInfo()
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean.Issue>(callback){
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean.Issue?, msg: String?) {

                    }

                })
    }

    override fun getMoreIssueList(callback: AttentionModel.AttentionListCallback) {

    }
}