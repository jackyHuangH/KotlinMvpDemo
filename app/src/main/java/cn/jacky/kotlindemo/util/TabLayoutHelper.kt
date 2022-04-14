package cn.jacky.kotlindemo.util

import android.annotation.SuppressLint
import android.os.Build
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.zenchn.support.kit.AndroidKit
import java.lang.reflect.Field

/**
 * Created by xuhao on 2017/12/7.
 * desc:反射重新设置tabLayout宽度
 */
object TabLayoutHelper {

    @SuppressLint("ObsoleteSdkInt")
    fun setUpIndicatorWidth(tabLayout: TabLayout) {
        val tabLayoutClass = tabLayout.javaClass
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip")
            tabStrip?.isAccessible = true

            var layout: LinearLayout? = null

            if (tabStrip != null) {
                layout = tabStrip.get(tabLayout) as? LinearLayout
            }
            layout?.apply {
                for (i in 0 until this.childCount) {
                    val child = layout.getChildAt(i)
                    child.setPadding(0, 0, 0, 0)
                    val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        params.marginStart = AndroidKit.Dimens.dp2px(50)
                        params.marginEnd = AndroidKit.Dimens.dp2px(50)
                    }
                    child.layoutParams = params
                    child.invalidate()
                }
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

    }
}