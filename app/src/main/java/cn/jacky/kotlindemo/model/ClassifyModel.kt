package cn.jacky.kotlindemo.model

import com.zenchn.apilib.callback.rx.RxApiCallback
import cn.jacky.kotlindemo.api.bean.CategoryBean
import cn.jacky.kotlindemo.api.bean.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/24/024
 * desc  ：分类相关接口
 * record：
 */
interface ClassifyModel {
    /**
     * 获取分类列表
     */
    fun getClassifyList(callback: ClassifyListCallback)

    /**
     * 获取分类详情列表
     */
    fun getClassifyDetailList(categoryId: Long, callback: ClassifyDetailListCallback)

    /**
     * 获取列表更多数据
     */
    fun loadMoreClassifyDetailData(nextPageUrl: String, callback: ClassifyDetailListCallback)

    interface ClassifyListCallback : RxApiCallback {

        fun onGetClassifyListSuccess(classifyList: List<CategoryBean>)
    }

    interface ClassifyDetailListCallback : RxApiCallback {

        fun onRequestDetailListSuccess(issue: HomeBean.Issue)

        fun onLoadMoreDataSuccess(issue: HomeBean.Issue)
    }
}