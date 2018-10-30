package com.huangzj.kotlindemo.ui.base

import com.zenchn.support.base.IActivity

/**
 * @author:Hzj
 * @date  :2018/10/30/030
 * desc  ：
 * record：
 */
interface BaseView : IActivity {
    fun onApiFailure()

    fun onApiGrantRefuse()
}