package cn.jacky.kotlindemo.mvp.baseview

import com.zenchn.support.base.IActivity

/**
 * @author:Hzj
 * @date  :2018/10/30/030
 * desc  ：
 * record：
 */
interface IView : IActivity {
    fun onApiFailure()

    fun onApiGrantRefuse()
}