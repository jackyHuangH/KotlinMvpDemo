package cn.jacky.kotlindemo.model

import cn.jacky.kotlindemo.api.bean.HomeBean
import com.zenchn.apilib.callback.rx.RxApiCallback

/**
 * @author:Hzj
 * @date  :2018/12/12/012
 * desc  ：首页model
 * record：
 */
interface HomeModel {
    fun refreshHomeData(num: Int, callback: RxApiCallback, onSuccess: (HomeBean) -> Unit)

    fun loadMoreHomeData(nextPageUrl: String, callback: RxApiCallback, onSuccess: (HomeBean) -> Unit)
}