package cn.jacky.kotlindemo.model.impl

import cn.jacky.kotlindemo.api.service.OpenEyeService
import cn.jacky.kotlindemo.model.SearchModel
import com.zenchn.apilib.callback.rx.RxHttpDataObserver
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.entity.HomeBean
import com.zenchn.apilib.retrofit.RetrofitManager

/**
 * @author:Hzj
 * @date  :2018/12/18/018
 * desc  ：
 * record：
 */
class SearchModelImpl : SearchModel {

    override fun getHotWordList(callback: SearchModel.HotWordListCallback) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getHotWord()
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<ArrayList<String>>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: ArrayList<String>?, msg: String?) {
                        if (success) {
                            data?.let { callback.onGetHotWordListSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

    override fun refreshSearchData(keyword: String, num: Int, callback: SearchModel.SearchListCallback) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getSearchData(keyword)
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean.Issue>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean.Issue?, msg: String?) {
                        if (success) {
                            data?.let { callback.onSearchListSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }

    override fun loadMoreSearchData(nextPageUrl: String, callback: SearchModel.SearchListCallback) {
        RetrofitManager
                .getInstance()
                .create(OpenEyeService::class.java)
                .getIssueData(nextPageUrl)
                .compose(RxSchedulerController.applySchedulers())
                .subscribe(object : RxHttpDataObserver<HomeBean.Issue>(callback) {
                    override fun onHttpResponseResult(success: Boolean, data: HomeBean.Issue?, msg: String?) {
                        if (success) {
                            data?.let { callback.onSearchMoreListSuccess(it) }
                        } else {
                            callback.onApiFailure(msg)
                        }
                    }
                })
    }
}