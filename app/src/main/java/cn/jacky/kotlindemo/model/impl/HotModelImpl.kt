package cn.jacky.kotlindemo.model.impl

import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.api.bean.TabInfoBean
import cn.jacky.kotlindemo.api.service.OpenEyeService
import cn.jacky.kotlindemo.model.HotModel
import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/26/026
 * desc  ：
 * record：
 */
class HotModelImpl : HotModel {

    override fun getTabInfo(callback: RxApiCallback, successBlog: (TabInfoBean) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getRankList()
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<TabInfoBean>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: TabInfoBean?, msg: String?) {
                        if (success) {
                            data?.let { successBlog(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

    override fun requestRankList(apiUrl: String, callback: RxApiCallback, successBlog: (HomeBean.Issue) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getIssueData(apiUrl)
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean.Issue>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean.Issue?, msg: String?) {
                        if (success) {
                            data?.let { successBlog(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

}