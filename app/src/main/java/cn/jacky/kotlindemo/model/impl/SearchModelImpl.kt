package cn.jacky.kotlindemo.model.impl

import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.api.service.OpenEyeService
import cn.jacky.kotlindemo.model.SearchModel
import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/18/018
 * desc  ：
 * record：
 */
class SearchModelImpl : SearchModel {

    override fun getHotWordList(callback: RxApiCallback, onSuccess: (ArrayList<String>) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getHotWord()
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<ArrayList<String>>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: ArrayList<String>?, msg: String?) {
                        if (success) {
                            data?.let { onSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

    override fun refreshSearchData(keyword: String, num: Int, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getSearchData(keyword)
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

    override fun loadMoreSearchData(nextPageUrl: String, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit) {
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