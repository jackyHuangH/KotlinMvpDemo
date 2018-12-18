package cn.jacky.kotlindemo.mvp.discovery

import android.os.Bundle
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：发现
 * record：
 */
class DiscoveryFragment : BaseFragment() {
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_discovery

    override fun initWidget() {

    }

    override fun lazyLoad() {

    }
}