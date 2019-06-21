package cn.jacky.kotlindemo.mvp.basepresenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：引入lifecycle管理生命周期
 * record：
 */
interface IPresenter : LifecycleObserver, IApiOAuth {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event)
}