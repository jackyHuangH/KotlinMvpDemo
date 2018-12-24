package cn.jacky.kotlindemo.wrapper.snaphelper

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @author:Hzj
 * @date :2018/12/24/024
 * desc  ：
 * A RecyclerView that only handles scroll events with the same orientation of its LayoutManager.
 * Avoids situations where nested recyclerviews don't receive touch events properly:
 *
 *
 * record：
 */

class OrientationAwareRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                             defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {
    private var lastX = 0.0f
    private var lastY = 0.0f
    private var orientation = RecyclerView.VERTICAL
    private var scrolling = false

    init {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrolling = newState != RecyclerView.SCROLL_STATE_IDLE
            }
        })
    }

    override fun setLayoutManager(layout: RecyclerView.LayoutManager?) {
        super.setLayoutManager(layout)
        if (layout is LinearLayoutManager) {
            orientation = layout.orientation
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        var allowScroll = true

        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = e.x
                lastY = e.y
                // If we were scrolling, stop now by faking a touch release
                if (scrolling) {
                    val newEvent = MotionEvent.obtain(e)
                    newEvent.action = MotionEvent.ACTION_UP
                    return super.onInterceptTouchEvent(newEvent)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                // We're moving, so check if we're trying
                // to scroll vertically or horizontally so we don't intercept the wrong event.
                val currentX = e.x
                val currentY = e.y
                val dx = Math.abs(currentX - lastX)
                val dy = Math.abs(currentY - lastY)
                allowScroll = if (dy > dx)
                    orientation == RecyclerView.VERTICAL
                else
                    orientation == RecyclerView.HORIZONTAL
            }
            else -> {
            }
        }

        return if (!allowScroll) {
            false
        } else super.onInterceptTouchEvent(e)

    }
}
