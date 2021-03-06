package cn.jacky.kotlindemo.wrapper.snaphelper


import android.support.v4.text.TextUtilsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import java.util.*

/**
 * @author:Hzj
 * @date :2018/12/24/024
 * desc  ：
 * record：
 */
class GravityDelegate(private val gravity: Int, private var snapLastItem: Boolean,
                      private val listener: GravitySnapHelper.SnapListener?) {
    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null
    private var isRtl: Boolean = false
    private var snapping: Boolean = false
    private var lastSnappedPosition: Int = 0
    private var recyclerView: RecyclerView? = null
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE && snapping && listener != null) {
                if (lastSnappedPosition != RecyclerView.NO_POSITION) {
                    listener.onSnap(lastSnappedPosition)
                }
                snapping = false
            }
        }
    }

    init {
        if (gravity != Gravity.START && gravity != Gravity.END
                && gravity != Gravity.BOTTOM && gravity != Gravity.TOP) {
            throw IllegalArgumentException("Invalid gravity value. Use START " + "| END | BOTTOM | TOP constants")
        }
    }

    fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView != null) {
            recyclerView.onFlingListener = null
            if (gravity == Gravity.START || gravity == Gravity.END) {
                isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL
            }
            if (listener != null) {
                recyclerView.addOnScrollListener(scrollListener)
            }
            this.recyclerView = recyclerView
        }
    }

    fun smoothScrollToPosition(position: Int) {
        scrollTo(position, true)
    }

    fun scrollToPosition(position: Int) {
        scrollTo(position, false)
    }

    private fun scrollTo(position: Int, smooth: Boolean) {
        if (recyclerView!!.layoutManager != null) {
            val viewHolder = recyclerView!!.findViewHolderForAdapterPosition(position)
            if (viewHolder != null) {
                val distances = calculateDistanceToFinalSnap(recyclerView!!.layoutManager,
                        viewHolder.itemView)
                if (smooth) {
                    recyclerView!!.smoothScrollBy(distances[0], distances[1])
                } else {
                    recyclerView!!.scrollBy(distances[0], distances[1])
                }
            } else {
                if (smooth) {
                    recyclerView!!.smoothScrollToPosition(position)
                } else {
                    recyclerView!!.scrollToPosition(position)
                }
            }
        }
    }

    fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager,
                                     targetView: View): IntArray {
        val out = IntArray(2)

        if (layoutManager !is LinearLayoutManager) {
            return out
        }

        if (layoutManager.canScrollHorizontally()) {
            if (isRtl && gravity == Gravity.END || !isRtl && gravity == Gravity.START) {
                out[0] = distanceToStart(targetView, layoutManager, getHorizontalHelper(layoutManager))
            } else {
                out[0] = distanceToEnd(targetView, layoutManager, getHorizontalHelper(layoutManager))
            }
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            if (gravity == Gravity.TOP) {
                out[1] = distanceToStart(targetView, layoutManager, getVerticalHelper(layoutManager))
            } else { // BOTTOM
                out[1] = distanceToEnd(targetView, layoutManager, getVerticalHelper(layoutManager))
            }
        } else {
            out[1] = 0
        }

        return out
    }

    fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager !is LinearLayoutManager) {
            return null
        }
        var snapView: View? = null
        when (gravity) {
            Gravity.START -> snapView = findEdgeView(layoutManager, getHorizontalHelper(layoutManager), true)
            Gravity.END -> snapView = findEdgeView(layoutManager, getHorizontalHelper(layoutManager), false)
            Gravity.TOP -> snapView = findEdgeView(layoutManager, getVerticalHelper(layoutManager), true)
            Gravity.BOTTOM -> snapView = findEdgeView(layoutManager, getVerticalHelper(layoutManager), false)
        }
        snapping = snapView != null
        if (snapView != null) {
            lastSnappedPosition = recyclerView!!.getChildAdapterPosition(snapView)
        }
        return snapView
    }

    fun enableLastItemSnap(snap: Boolean) {
        snapLastItem = snap
    }

    private fun distanceToStart(targetView: View, lm: LinearLayoutManager,
                                helper: OrientationHelper): Int {
        val pos = recyclerView!!.getChildLayoutPosition(targetView)
        val distance: Int
        if ((pos == 0 && (!isRtl || lm.reverseLayout) || pos == lm.itemCount - 1 && (isRtl || lm.reverseLayout)) && !recyclerView!!.clipToPadding) {
            val childStart = helper.getDecoratedStart(targetView)
            if (childStart >= helper.startAfterPadding / 2) {
                distance = childStart - helper.startAfterPadding
            } else {
                distance = childStart
            }
        } else {
            distance = helper.getDecoratedStart(targetView)
        }
        return distance
    }

    private fun distanceToEnd(targetView: View, lm: LinearLayoutManager,
                              helper: OrientationHelper): Int {
        val pos = recyclerView!!.getChildLayoutPosition(targetView)
        val distance: Int

        // The last position or the first position
        // (when there's a reverse layout or we're on RTL mode) must collapse to the padding edge.
        if ((pos == 0 && (isRtl || lm.reverseLayout) || pos == lm.itemCount - 1 && (!isRtl || lm.reverseLayout)) && !recyclerView!!.clipToPadding) {
            val childEnd = helper.getDecoratedEnd(targetView)
            if (childEnd >= helper.end - (helper.end - helper.endAfterPadding) / 2) {
                distance = helper.getDecoratedEnd(targetView) - helper.end
            } else {
                distance = childEnd - helper.endAfterPadding
            }
        } else {
            distance = helper.getDecoratedEnd(targetView) - helper.end
        }
        return distance
    }

    /**
     * Returns the first view that we should snap to.
     *
     * @param lm     the recyclerview's layout manager
     * @param helper orientation helper to calculate view sizes
     * @return the first view in the LayoutManager to snap to
     */
    private fun findEdgeView(lm: LinearLayoutManager, helper: OrientationHelper, start: Boolean): View? {
        if (lm.childCount == 0) {
            return null
        }

        // If we're at the end of the list, we shouldn't snap
        // to avoid having the last item not completely visible.
        if (isAtEndOfList(lm) && !snapLastItem) {
            return null
        }

        var edgeView: View? = null
        var distanceToEdge = Integer.MAX_VALUE

        for (i in 0 until lm.childCount) {
            val currentView = lm.getChildAt(i)
            val currentViewDistance: Int
            if (start && !isRtl || !start && isRtl) {
                currentViewDistance = Math.abs(helper.getDecoratedStart(currentView))
            } else {
                currentViewDistance = Math.abs(helper.getDecoratedEnd(currentView) - helper.end)
            }
            if (currentViewDistance < distanceToEdge) {
                distanceToEdge = currentViewDistance
                edgeView = currentView
            }
        }
        return edgeView
    }

    private fun isAtEndOfList(lm: LinearLayoutManager): Boolean {
        return if (!lm.reverseLayout && gravity == Gravity.START || lm.reverseLayout && gravity == Gravity.END) {
            lm.findLastCompletelyVisibleItemPosition() == lm.itemCount - 1
        } else {
            lm.findFirstCompletelyVisibleItemPosition() == 0
        }
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return verticalHelper!!
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper!!
    }
}
