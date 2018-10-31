package com.huangzj.kotlindemo.ui.fragment

import android.os.Bundle
import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.ui.base.BaseFragment

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：每日精选
 * record：
 */
class HomeFragment : BaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initWidget() {

    }
}