package cn.jacky.kotlindemo.mvp.discovery.classify

import cn.jacky.kotlindemo.mvp.basepresenter.IPresenter
import cn.jacky.kotlindemo.mvp.baseview.IView
import com.zenchn.apilib.entity.CategoryBean

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
interface ClassifyContract {
    interface View : IView {
        fun showClassifyList(categoryList: List<CategoryBean>)
    }

    interface Presenter : IPresenter {
        fun getClassifyList()
    }
}