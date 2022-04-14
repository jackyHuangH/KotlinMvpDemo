package cn.jacky.kotlindemo.app

import androidx.multidex.MultiDexApplication

/**
 * @author:Hzj
 * @date  :2018/10/23/023
 * desc  ：
 * record：
 */
class App : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        //其他初始化
        ApplicationKit.instance.initKit(this)
    }
}