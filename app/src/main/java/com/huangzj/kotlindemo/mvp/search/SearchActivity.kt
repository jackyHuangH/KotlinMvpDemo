package com.huangzj.kotlindemo.mvp.search

import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.mvp.baseview.BaseActivity

/**
 * @author:Hzj
 * @date  :2018/12/17/017
 * desc  ：
 * record：
 */
class SearchActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_search

    override fun initWidget() {
        setupTransition()
    }

    private fun setupTransition() {
        //设置入场出场动画

    }

    companion object {

    }
}