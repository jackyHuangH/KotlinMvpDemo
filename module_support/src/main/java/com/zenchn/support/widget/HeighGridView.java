package com.zenchn.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Scroll嵌套显示GridView,无法滑动
 * 完全展示
 *
 * @author hzj
 */
public class HeighGridView extends GridView {

    public HeighGridView(Context context) {
        super(context);
    }

    public HeighGridView(Context context, AttributeSet as) {
        super(context, as);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
