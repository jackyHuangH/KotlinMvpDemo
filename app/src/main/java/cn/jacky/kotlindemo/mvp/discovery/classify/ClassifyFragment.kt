package cn.jacky.kotlindemo.mvp.discovery.classify

import android.os.Bundle
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：分类
 * record：
 */
class ClassifyFragment : BaseFragment() {

    companion object {
        fun getInstance(): ClassifyFragment {
            val fragment = ClassifyFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun lazyLoad() {

    }

    override fun getLayoutRes(): Int = R.layout.layout_recycler_view

    override fun initWidget() {
    }
}