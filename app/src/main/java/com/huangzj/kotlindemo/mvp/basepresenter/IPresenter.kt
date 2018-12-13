package com.huangzj.kotlindemo.mvp.basepresenter

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：
 * record：
 */
interface IPresenter : IApiOAuth {
    fun onStart()

    fun onDestroy()
}