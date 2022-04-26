package cn.jacky.kotlindemo.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by xuhao on 2017/11/14.
 */

fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun View.dip2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dip(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

/**
 * 数据流量格式化
 */
fun Context.dataFormat(total: Long): String {
    var result: String
    var speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}

/**
 * 按钮点击防抖
 */
fun View?.setOnAntiShakeClickListener(intervalMillis: Long = 1000, listener: (View) -> Unit) {
    /**
     * 最近一次点击的时间
     */
    var lastClickTime: Long = 0
    this?.setOnClickListener {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime >= intervalMillis) {
            listener.invoke(it)
            lastClickTime = currentTime
        }
    }
}

//适配高刷新率
fun Activity.adaptHighRefresh(){
    /*
      M 是 6.0，6.0修改了新的api，并且就已经支持修改window的刷新率了。
      但是6.0那会儿，也没什么手机支持高刷新率吧，所以也没什么人注意它。
      我更倾向于直接判断 O，也就是 Android 8.0，我觉得这个时候支持高刷新率的手机已经开始了。
      */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // 获取系统window支持的模式
        val modes = window.windowManager.defaultDisplay.supportedModes
        // 对获取的模式，基于刷新率的大小进行排序，从小到大排序
        modes.sortBy {
            it.refreshRate
        }
        window.let {
            val lp = it.attributes
            // 取出最大的那一个刷新率，直接设置给window
            lp.preferredDisplayModeId = modes.last().modeId
            it.attributes = lp
        }
    }
}


