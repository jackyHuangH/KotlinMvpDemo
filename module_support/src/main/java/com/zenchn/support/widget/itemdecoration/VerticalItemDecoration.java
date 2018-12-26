package com.zenchn.support.widget.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zenchn.support.kit.AndroidKit;

/**
 * Created by Administrator on 2016/11/2.
 * recyclerview item 头部或底部加间距
 */

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    private Context context;
    /**
     * 是顶部间距还是底部间距
     **/
    private boolean isTopDecoration;

    public VerticalItemDecoration(Context context, int spaceInDp, boolean isTopDecoration) {
        this.mSpace = AndroidKit.Dimens.dp2px(spaceInDp);
        this.isTopDecoration = isTopDecoration;
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isTopDecoration) {
            outRect.top = mSpace;
        } else {
            outRect.bottom = mSpace;
        }
    }

}
