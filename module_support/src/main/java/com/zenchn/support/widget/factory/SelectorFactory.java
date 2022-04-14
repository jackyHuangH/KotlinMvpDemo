package com.zenchn.support.widget.factory;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * 作    者：wangr on 2017/6/9 17:14
 * 描    述：
 * 修订记录：
 */

public class SelectorFactory {

    /**
     * 生成一个图片选择器
     *
     * @param normalDrawable
     * @param selectedDrawable
     * @return
     */
    public static StateListDrawable createSelector(@NonNull Drawable normalDrawable, @NonNull Drawable selectedDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_pressed}, selectedDrawable);
        drawable.addState(new int[0], normalDrawable);
        return drawable;
    }

    /**
     * 生成一个颜色选择器
     *
     * @param normalColor
     * @param selectedColor
     * @return
     */
    public static ColorStateList createSelector(@ColorInt int normalColor, @ColorInt int selectedColor) {
        int[] colors = new int[]{selectedColor, selectedColor, normalColor};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[0];
        return new ColorStateList(states, colors);
    }
}
