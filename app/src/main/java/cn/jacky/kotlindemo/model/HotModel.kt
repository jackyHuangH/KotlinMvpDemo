package cn.jacky.kotlindemo.model

import com.hazz.kotlinmvp.mvp.model.bean.TabInfoBean
import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/26/026
 * desc  ：热门相关接口
 * record：
 */
interface HotModel {
    /**
     * 获取热门排行tab信息
     */
    fun getTabInfo(callback: TabInfoCallback)

    /**
     * 获取排行列表
     */
    fun requestRankList(apiUrl: String, callback: RankListCallback)

    interface TabInfoCallback : RxApiCallback {

        fun onGetTabInfoSuccess(tabInfo: TabInfoBean)
    }

    interface RankListCallback : RxApiCallback {

        fun onGetRankListSuccess(issue: HomeBean.Issue)
    }
}