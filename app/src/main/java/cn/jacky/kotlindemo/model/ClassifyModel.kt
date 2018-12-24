package cn.jacky.kotlindemo.model

import com.zenchn.apilib.callback.rx.RxApiCallback
import com.zenchn.apilib.entity.CategoryBean

/**
 * @author:Hzj
 * @date  :2018/12/24/024
 * desc  ：
 * record：
 */
interface ClassifyModel {
    fun getClassifyList(callback: ClassifyListCallback)

    interface ClassifyListCallback : RxApiCallback {

        fun onGetClassifyListSuccess(classifyList: List<CategoryBean>)
    }
}