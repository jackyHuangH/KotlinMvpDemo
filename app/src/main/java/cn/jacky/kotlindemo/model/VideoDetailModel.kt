package cn.jacky.kotlindemo.model

import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/20/020
 * desc  ：视频详情model
 * record：
 */
interface VideoDetailModel {
    fun getRelatedVideoList(id: Long, callback: RelatedVideoListCallback)

    interface RelatedVideoListCallback : RxApiCallback {

        fun onGetRelatedVideoListSuccess(issue: HomeBean.Issue)
    }
}