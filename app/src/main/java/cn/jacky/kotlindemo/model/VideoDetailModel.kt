package cn.jacky.kotlindemo.model

import cn.jacky.kotlindemo.api.bean.HomeBean
import com.zenchn.apilib.callback.rx.RxApiCallback

/**
 * @author:Hzj
 * @date  :2018/12/20/020
 * desc  ：视频详情model
 * record：
 */
interface VideoDetailModel {
    fun getRelatedVideoList(id: Long, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit)
}