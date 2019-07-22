package cn.jacky.kotlindemo.model

import cn.jacky.kotlindemo.api.bean.HomeBean
import com.zenchn.apilib.callback.rx.RxApiCallback

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：
 * record：
 */
interface AttentionModel {
    fun requestAttenionList(callback:RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit)

    fun getMoreIssueList(callback: RxApiCallback, nextPageUrl: String, onSuccess: (HomeBean.Issue) -> Unit)
}