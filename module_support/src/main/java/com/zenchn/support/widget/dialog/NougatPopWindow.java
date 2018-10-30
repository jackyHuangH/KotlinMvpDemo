package com.zenchn.support.widget.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 想要popWindows正常在Android7.0系统正常显示，我们需要重写popWindows的showAsDropDown方法
 */

public class NougatPopWindow extends PopupWindow {
    public NougatPopWindow(Context context) {
        super(context);
    }

    public NougatPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NougatPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NougatPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NougatPopWindow() {
    }

    public NougatPopWindow(View contentView) {
        super(contentView);
    }

    public NougatPopWindow(int width, int height) {
        super(width, height);
    }

    public NougatPopWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public NougatPopWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 23) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}
