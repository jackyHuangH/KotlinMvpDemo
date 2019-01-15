package cn.jacky.kotlindemo.model

import com.zenchn.apilib.callback.rx.RxApiCallback
import cn.jacky.kotlindemo.api.bean.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/12/012
 * desc  ：搜索model
 * record：
 */
interface SearchModel {
    fun getHotWordList(callback: HotWordListCallback)

    fun refreshSearchData(keyword: String, num: Int, callback: SearchListCallback)

    fun loadMoreSearchData(nextPageUrl: String, callback: SearchListCallback)

    interface SearchListCallback : RxApiCallback {
        fun onSearchListSuccess(issue: HomeBean.Issue)

        fun onSearchMoreListSuccess(issue: HomeBean.Issue)
    }

    interface HotWordListCallback : RxApiCallback {

        fun onGetHotWordListSuccess(hotWords: ArrayList<String>)
    }
}