package com.zenchn.support.widget.enteringlayout;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作    者：wangr on 2017/9/13 17:12
 * 描    述：
 * 修订记录：
 */

public class FocusEditText extends android.support.v7.widget.AppCompatEditText {

    public Handler mHandler = new Handler(Looper.getMainLooper());

    public FocusEditText(Context context) {
        super(context);
    }

    public FocusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Editable text = getText();
        if (!TextUtils.isEmpty(text)) {
            Selection.setSelection(text, 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Editable text = getText();
                    if (!TextUtils.isEmpty(text)) {
                        setSelection(text.length());
                    }
                }
            }, 50);
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }


}
