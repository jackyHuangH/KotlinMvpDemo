package cn.jacky.kotlindemo.model

import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：
 * record：
 */
interface AttentionModel {
    fun requestAttenionList(callback: AttentionListCallback)

    fun getMoreIssueList(nextPageUrl: String, callback: AttentionListCallback)

    interface AttentionListCallback : RxApiCallback {

        fun onRefreshAttentionListSuccess(issue: HomeBean.Issue)

        fun onGetMoreListSuccess(issue: HomeBean.Issue)
    }
}