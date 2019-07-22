package cn.jacky.kotlindemo.model.impl

import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.api.service.OpenEyeService
import cn.jacky.kotlindemo.model.VideoDetailModel
import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/20/020
 * desc  ：
 * record：
 */
class VideoDetailModelImpl : VideoDetailModel {

    override fun getRelatedVideoList(id: Long, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getRelatedData(id)
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