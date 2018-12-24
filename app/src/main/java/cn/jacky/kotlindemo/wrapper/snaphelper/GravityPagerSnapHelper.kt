package cn.jacky.kotlindemo.wrapper.snaphelper

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author:Hzj
 * @date :2018/12/24/024
 * desc  ：
 * record：
 */
class GravityPagerSnapHelper @JvmOverloads constructor(gravity: Int, enableSnapLastItem: Boolean = false,
                                                       snapListener: GravitySnapHelper.SnapListener? = null) : PagerSnapHelper() {
    private val delegate: GravityDelegate

    init {
        delegate = GravityDelegate(gravity, enableSnapLastItem, snapListener)
    }

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView != null && (recyclerView.layoutManager !is LinearLayoutManager || recyclerView.layoutManager is GridLayoutManager)) {
            throw IllegalStateException("GravityPagerSnapHelper needs a RecyclerView" + " with a LinearLayoutManager")
        }
        delegate.attachToRecyclerView(recyclerView)
        super.attachToRecyclerView(recyclerView)
    }

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager,
                                              targetView: View): IntArray? {
        return delegate.calculateDistanceToFinalSnap(layoutManager, targetView)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return delegate.findSnapView(layoutManager)
    }

    /**
     * Enable snapping of the last item that's snappable.
     * The default value is false, because you can't see the last item completely
     * if this is enabled.
     *
     * @param snap true if you want to enable snapping of the last snappable item
     */
    fun enableLastItemSnap(snap: Boolean) {
        delegate.enableLastItemSnap(snap)
    }

    fun smoothScrollToPosition(position: Int) {
        delegate.smoothScrollToPosition(position)
    }

    fun scrollToPosition(position: Int) {
        delegate.scrollToPosition(position)
    }
}
