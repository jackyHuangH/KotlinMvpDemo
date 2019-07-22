package cn.jacky.kotlindemo.model

import cn.jacky.kotlindemo.api.bean.CategoryBean
import cn.jacky.kotlindemo.api.bean.HomeBean
import com.zenchn.apilib.callback.rx.RxApiCallback

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
    fun getClassifyList(callback: RxApiCallback, onSuccess: (List<CategoryBean>) -> Unit)

    /**
     * 获取分类详情列表
     */
    fun getClassifyDetailList(categoryId: Long, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit)

    /**
     * 获取列表更多数据
     */
    fun loadMoreClassifyDetailData(nextPageUrl: String, callback: RxApiCallback, onSuccess: (HomeBean.Issue) -> Unit)

}