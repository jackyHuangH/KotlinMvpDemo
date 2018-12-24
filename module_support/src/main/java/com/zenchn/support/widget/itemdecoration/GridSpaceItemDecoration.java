package com.zenchn.support.widget.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zenchn.support.kit.AndroidKit;

/**
 * Created by Hzj on 2017/8/21.
 * recyclerview grid,vertical才有效
 */

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int numOfColumns;
    private int listSize;
    private int offsetInDp;//gridview 间距
    private boolean isBottomRow = false;

    public GridSpaceItemDecoration(int numOfColumns, int listSize, int spanInPix) {
        this.numOfColumns = numOfColumns;
        this.listSize = listSize;
        this.offsetInDp = AndroidKit.Dimens.px2dp(spanInPix);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        int pos = parent.getChildAdapterPosition(view);

        // only do left/right spacing if grid or horizontal content_bottomsheet_dialog
        outRect.left = offsetInDp;
        outRect.right = offsetInDp;

        // only do top/bottom spacing if grid or vertical content_bottomsheet_dialog
        boolean isNotTopRow = pos >= numOfColumns;

        // Don't add top spacing to top row
        if (isNotTopRow) {
            outRect.top = offsetInDp;
        }

        int columnIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();

        if (pos >= (listSize - numOfColumns) && columnIndex == 0) {
            isBottomRow = true;
        }

        // Don't add bottom spacing to bottom row
        if (!isBottomRow && pos < (listSize - numOfColumns)) {
            outRect.bottom = offsetInDp;
        }
    }
}
