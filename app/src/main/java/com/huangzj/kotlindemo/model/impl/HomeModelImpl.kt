package com.huangzj.kotlindemo.model.impl

import com.huangzj.kotlindemo.api.service.OpenEyeService
import com.huangzj.kotlindemo.model.HomeModel
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.entity.HomeBean
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/12/012
 * desc  ：
 * record：
 */
class HomeModelImpl : HomeModel {

    override fun refreshHomeData(num: Int, callback: HomeModel.HomeListCallback) {
        RetrofitManager.getInstance()
                .create(OpenEyeService::class.java)
                .getFirstHomeData(num)
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean?, msg: String?) {
                        if (success) {
                            data?.let { callback.onGetHomeListSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

    override fun loadMoreHomeData(nextPageUrl: String, callback: HomeModel.HomeListCallback) {
        RetrofitManager.getInstance()
                .create(OpenEyeService::class.java)
                .getMoreHomeData(nextPageUrl)
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean?, msg: String?) {
                        if (success) {
                            data?.let { callback.onGetHomeMoreListSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }
}