package cn.jacky.kotlindemo.model.impl

import cn.jacky.kotlindemo.api.service.OpenEyeService
import cn.jacky.kotlindemo.model.HotModel
import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.entity.HomeBean
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/26/026
 * desc  ：
 * record：
 */
class HotModelImpl : HotModel {

    override fun getTabInfo(callback: HotModel.TabInfoCallback) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getRankList()
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<TabInfoBean>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: TabInfoBean?, msg: String?) {
                        if (success) {
                            data?.let { callback.onGetTabInfoSuccess(data) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

    override fun requestRankList(apiUrl: String, callback: HotModel.RankListCallback) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getIssueData(apiUrl)
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean.Issue>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean.Issue?, msg: String?) {
                        if (success) {
                            data?.let { callback.onGetRankListSuccess(data) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }
}