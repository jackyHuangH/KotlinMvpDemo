package com.huangzj.kotlindemo

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
}