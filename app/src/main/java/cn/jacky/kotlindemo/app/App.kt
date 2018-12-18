package cn.jacky.kotlindemo.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * @author:Hzj
 * @date  :2018/10/23/023
 * desc  ：
 * record：
 */
class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        //其他初始化
        ApplicationKit.instance.initKit(this)
    }
}