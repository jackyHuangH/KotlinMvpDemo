package cn.jacky.kotlindemo.model

import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/12/012
 * desc  ：首页model
 * record：
 */
interface HomeModel {
    fun refreshHomeData(num: Int, callback: HomeListCallback)

    fun loadMoreHomeData(nextPageUrl: String, callback: HomeListCallback)

    interface HomeListCallback : RxApiCallback {
        fun onRefreshHomeListSuccess(homeBean: HomeBean)

        fun onGetHomeMoreListSuccess(homeBean: HomeBean)
    }
}