package cn.jacky.kotlindemo.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * @author:Hzj
 * @date  :2018/12/18/018
 * desc  ：动画工具类
 * record：
 */
object ViewAnimUtil {
    interface OnRevealAnimationListener {
        fun onRevealShow()

        fun onRevealHide()
    }


    /**
     * 显示圆形揭露动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateRevealShow(context: Context,
                          view: View,
                          startRadius: Float,
                          @ColorRes backgroundColor: Int,
                          listener: OnRevealAnimationListener) {

        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2
        val endRadius = Math.hypot(view.width.toDouble(), view.height.toDouble()).toFloat()

        val reveal = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, endRadius)
        reveal.duration = 300
        reveal.interpolator = AccelerateDecelerateInterpolator()
        reveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                view.visibility = View.VISIBLE
                listener.onRevealShow()
            }

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                view.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            }
        })

        reveal.start()
    }

    // 圆圈凝聚效果
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateRevealHide(context: Context,
                          view: View,
                          finalRadius: Int,
                          @ColorRes color: Int,
                          listener: OnRevealAnimationListener) {
        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2
        val initialRadius = Math.min(view.width, view.height)
        // 与入场动画的区别就是圆圈起始和终止的半径相反
        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius.toFloat(), finalRadius.toFloat())
        anim.duration = 300
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                view.setBackgroundColor(ContextCompat.getColor(context, color))
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                listener.onRevealHide()
                view.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }
}