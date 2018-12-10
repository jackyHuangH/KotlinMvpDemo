package com.zenchn.support.widget.factory;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作    者：wangr on 2017/7/7 16:11
 * 描    述：
 * 修订记录：
 */

public class ViewFactory {

    public static TextView getTextView(@NonNull Context context, @Nullable String text, @ColorInt int color, int textSize) {
        return getTextView(context, text, color, textSize, null);
    }

    public static TextView getTextView(@NonNull Context context, @Nullable String text, @ColorInt int color, int textSize, @Nullable Drawable backgroundDrawable) {
        return getTextView(context, text, ColorStateList.valueOf(color), textSize, backgroundDrawable);
    }

    public static TextView getTextView(@NonNull Context context, @Nullable String text, @NonNull ColorStateList color, int textSize, @Nullable Drawable backgroundDrawable) {
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        }
        tv.setTextColor(color);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (backgroundDrawable != null) {
            tv.setBackgroundDrawable(backgroundDrawable);
        }
        return tv;
    }

//    public static Button getTextButton(@NonNull Context context, @Nullable String text, @ColorInt int color, int textSize) {
//        return getTextButton(context, text, color, textSize, null);
//    }
//
//    public static Button getTextButton(@NonNull Context context, @Nullable String text, @ColorInt int color, int textSize, @Nullable Drawable backgroundDrawable) {
//        return getTextButton(context, text, ColorStateList.valueOf(color), textSize, backgroundDrawable);
//    }
//
//    public static Button getTextButton(@NonNull Context context, @Nullable String text, @NonNull ColorStateList color, int textSize, @Nullable Drawable backgroundDrawable) {
//        Button bt = new Button(context);
//        bt.setGravity(Gravity.CENTER);
//        if (!TextUtils.isEmpty(text))
//            bt.setText(text);
//        bt.setTextColor(color);
//        bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//        bt.setBackgroundDrawable(backgroundDrawable);
//        return bt;
//    }

    public static ImageView getImageView(@NonNull Context context, @NonNull Drawable nextIcon) {
        ImageView iv = new ImageView(context);
        iv.setImageDrawable(nextIcon);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return iv;
    }

    public static ImageButton getImageButton(@NonNull Context context, @NonNull Drawable nextIcon) {
        ImageButton ib = new ImageButton(context);
        ib.setImageDrawable(nextIcon);
        ib.setBackground(null);
        ib.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return ib;
    }

    public static ImageButton getImageButton(@NonNull Context context, @NonNull Drawable nextIcon, @NonNull Drawable background) {
        ImageButton ib = new ImageButton(context);
        ib.setBackground(background);
        ib.setImageDrawable(nextIcon);
        ib.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return ib;
    }

}
