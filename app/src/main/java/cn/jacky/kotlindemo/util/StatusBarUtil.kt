package cn.jacky.kotlindemo.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import java.util.regex.Pattern

/**
 * 状态栏工具类
 */

class StatusBarUtil {


    companion object {
        private var DEFAULT_COLOR = 0
        private var DEFAULT_ALPHA = 0f//Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 0.2f : 0.3f;
        private val MIN_API = 19

        /** 判断是否Flyme4以上  */
        private val isFlyme4Later: Boolean
            get() = (Build.FINGERPRINT.contains("Flyme_OS_4")
                    || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
                    || Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE).matcher(Build.DISPLAY).find())

        /** 判断是否为MIUI6以上  */
        private val isMIUI6Later: Boolean
            get() {
                return try {
                    val clz = Class.forName("android.os.SystemProperties")
                    val mtd = clz.getMethod("get", String::class.java)
                    var `val` = mtd.invoke(null, "ro.miui.ui.version.name") as String
                    `val` = `val`.replace("[vV]".toRegex(), "")
                    val version = Integer.parseInt(`val`)
                    version >= 6
                } catch (e: Exception) {
                    false
                }

            }

        /** 增加View的paddingTop,增加的值为状态栏高度  */
        fun setPadding(context: Context, view: View) {
            if (Build.VERSION.SDK_INT >= MIN_API) {
                view.setPadding(view.paddingLeft, view.paddingTop + getStatusBarHeight(context),
                        view.paddingRight, view.paddingBottom)
            }
        }

        /** 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度) */
        fun setPaddingSmart(context: Context, view: View) {
            if (Build.VERSION.SDK_INT >= MIN_API) {
                val lp = view.layoutParams
                if (lp != null && lp.height > 0) {
                    lp.height += getStatusBarHeight(context)//增高
                }
                view.setPadding(view.paddingLeft, view.paddingTop + getStatusBarHeight(context),
                        view.paddingRight, view.paddingBottom)
            }
        }

        /** 增加View的高度以及paddingTop,增加的值为状态栏高度.一般是在沉浸式全屏给ToolBar用的  */
        fun setHeightAndPadding(context: Context, view: View) {
            if (Build.VERSION.SDK_INT >= MIN_API) {
                val lp = view.layoutParams
                lp.height += getStatusBarHeight(context)//增高
                view.setPadding(view.paddingLeft, view.paddingTop + getStatusBarHeight(context),
                        view.paddingRight, view.paddingBottom)
            }
        }

        /** 增加View上边距（MarginTop）一般是给高度为 WARP_CONTENT 的小控件用的 */
        fun setMargin(context: Context, view: View) {
            if (Build.VERSION.SDK_INT >= MIN_API) {
                val lp = view.layoutParams
                if (lp is ViewGroup.MarginLayoutParams) {
                    lp.topMargin += getStatusBarHeight(context)//增高
                }
                view.layoutParams = lp
            }
        }

        /** 获取状态栏高度  */
        fun getStatusBarHeight(context: Context): Int {
            var result = 24
            val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            result = if (resId > 0) {
                context.resources.getDimensionPixelSize(resId)
            } else {
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        result.toFloat(), Resources.getSystem().displayMetrics).toInt()
            }
            return result
        }
    }

}
