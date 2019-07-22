package cn.jacky.kotlindemo.model

import cn.jacky.kotlindemo.api.bean.HomeBean
import com.zenchn.apilib.callback.rx.RxApiCallback

/**
 * @author:Hzj
 * @date  :2018/12/12/012
 * desc  ：搜索model
 * record：
 */
interface SearchModel {
    fun getHotWordList(callback: RxApiCallback, onSuccess: (ArrayList<String>) -> Unit)

    fun refreshSearchData(keyword: String, num: Int, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit)

    fun loadMoreSearchData(nextPageUrl: String, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit)

}