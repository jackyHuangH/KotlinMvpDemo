package com.zenchn.support.widget.factory;

import android.graphics.drawable.GradientDrawable;

/**
 * 作    者：wangr on 2017/4/25 9:59
 * 描    述：
 * 修订记录：
 */
public class DrawableFactory {

    /**
     * 生成一条线
     *
     * @param mSolidColor 填充颜色
     * @param mWidth      线条宽度(px)
     * @param mHeight     线条高度(px)
     * @return
     */
    public static GradientDrawable createDrawableLine(int mSolidColor, int mWidth, int mHeight) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(mSolidColor);
        drawable.setSize(mWidth, mHeight);
        return drawable;
    }

    /**
     * 生成一个背景图
     *
     * @param mSolidColor 填充颜色
     * @param mWidth      边框宽度(px)
     * @param mHeight     边框高度(px)
     * @param mLineWidth  边框线宽(px)
     * @param mLineColor  边框颜色
     * @param mRadius     边框圆角(px)
     * @return
     */
    public static GradientDrawable createBackgroundDrawable(int mSolidColor, int mWidth, int mHeight, int mLineWidth, int mLineColor, float mRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(mSolidColor);
        drawable.setSize(mWidth, mHeight);
        drawable.setStroke(mLineWidth, mLineColor);
        drawable.setCornerRadius(mRadius);
        return drawable;
    }

}
