package com.zenchn.support.widget.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zenchn.support.R;

/**
 * @author:Hzj
 * @date :2018/12/25/025
 * desc  ：recyclerView gridlayout 配合实现宫格效果
 * record：
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private Drawable mDivider;
    private static final int PADDING_IN_DP=10;

    /**
     * 默认透明分隔线，宽度3dp
     * @param context
     */
    public GridDividerItemDecoration(Context context) {
        this(context, R.drawable.shape_transparent_divider);
    }

    public GridDividerItemDecoration(Context context, @DrawableRes int dividerRes) {
        this.mContext = context;
        mDivider = ContextCompat.getDrawable(context, dividerRes);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    //列数
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    //画横线
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();

            if (isFirstColum(parent, i, getSpanCount(parent), childCount)) {
                //如果是第一列
                final int left = child.getLeft() + dip2px(PADDING_IN_DP);
                final int right = child.getRight() + params.rightMargin
                        + mDivider.getIntrinsicWidth();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else if (isLastColum(parent, i, getSpanCount(parent), childCount)) {
                //如果是最后一列
                final int left = child.getLeft();
                final int right = child.getRight() - dip2px(PADDING_IN_DP);
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else {
                final int left = child.getLeft();
                final int right = child.getRight() + mDivider.getIntrinsicWidth();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    //画竖线
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();

            if (i < getSpanCount(parent)) {
                //如果是第一行
                final int top = child.getTop() + child.getPaddingTop();
                final int bottom = child.getBottom();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else if (isLastRaw(parent, i, getSpanCount(parent), childCount)) {
                //如果是最后一行
                final int top = child.getTop();
                final int bottom = child.getBottom() - child.getPaddingBottom();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            } else {
                final int top = child.getTop();
                final int bottom = child.getBottom();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    //判断是第一列
    private boolean isFirstColum(RecyclerView parent, int pos, int spanCount,
                                 int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (pos + 1) % spanCount == 1;
        }
        return false;
    }

    //判断是最后一列
    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (pos + 1) % spanCount == 0;
        }
        return false;
    }

    //判断是最后一行
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
//            childCount = childCount - childCount % spanCount;
//            if (pos >= childCount)
//                return true;
            int ranger = childCount % spanCount;
            if (ranger == 0) {
                ranger = spanCount;
            }
            childCount = childCount - ranger;
            return pos >= childCount;
        }
        return false;
    }


    /**
     * //设置每个item的偏移量，从而展示分割线
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);

        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int marginRight = mDivider.getIntrinsicWidth();
        int marginBottom = mDivider.getIntrinsicHeight();
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
            // 如果是最后一行，则不需要绘制底部
            marginBottom = 0;
        }
        if (isLastColum(parent, itemPosition, spanCount, childCount)) {
            // 如果是最后一列，则不需要绘制右边
            marginRight = 0;
        }
        outRect.set(0, 0, marginRight, marginBottom);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
