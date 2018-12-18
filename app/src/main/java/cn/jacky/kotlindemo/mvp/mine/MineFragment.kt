package cn.jacky.kotlindemo.mvp.mine

import android.os.Bundle
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：每日精选
 * record：
 */
class MineFragment : BaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_mine

    override fun initWidget() {

    }

    override fun lazyLoad() {

    }
}