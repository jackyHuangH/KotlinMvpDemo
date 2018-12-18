package cn.jacky.kotlindemo.mvp.hot

import android.os.Bundle
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：热门
 * record：
 */
class HotFragment : BaseFragment() {
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_hot
    }

    override fun initWidget() {

    }

    override fun lazyLoad() {

    }
}