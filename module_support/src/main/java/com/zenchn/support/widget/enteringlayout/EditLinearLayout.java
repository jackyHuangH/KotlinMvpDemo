package com.zenchn.support.widget.enteringlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * 作    者：wangr on 2017/9/9 10:12
 * 描    述： 可以直接控制所有子控件是否可点击的LinearLayout
 * 修订记录：
 */
public class EditLinearLayout extends LinearLayout {

    //子控件是否可以接受点击事件（默认可以）
    private boolean mEditEnable = true;
    //父控件是否拦截事件（默认不拦截）
    private boolean mIntercepted;

    public EditLinearLayout(Context context) {
        super(context);
    }

    public EditLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EditLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //返回true则拦截子控件所有点击事件

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {//down事件的时候即可确定mTarget

            mIntercepted = !mEditEnable;

            int x = (int) ev.getRawX();
            int y = (int) ev.getRawY();

            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (isTouchPointInView(childAt, x, y)) {
                    if (isEditEnableView(childAt) || mEditEnable) {
                        childAt.requestFocus();
                        childAt.findFocus();
                        mIntercepted = false;
                        break;
                    }
                }
            }
        }
        return mIntercepted;
    }

    /**
     * 判断view时候具备点击能力
     *
     * @param view
     * @return
     */
    private boolean isEditEnableView(View view) {
        boolean isEditEnable = false;
        if (view != null && view instanceof EditEnableLayout) {
            EditEnableLayout editEnableLayout = (EditEnableLayout) view;
            isEditEnable = editEnableLayout.hasEditEnable();
        }
        return isEditEnable;
    }

    /**
     * 判断触摸点是否在view的区域内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null || view.getVisibility() != VISIBLE) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return (y >= top && y <= bottom && x >= left && x <= right);
    }

    public void setEditEnable(boolean editEnable) {
        if (editEnable) {
            requestAllFocusable();
        } else {
            clearAllFocus();
        }
        mEditEnable = editEnable;
    }

    /**
     * 子view实现该接口可以实现事件不拦截
     */
    public interface EditEnableLayout {

        boolean hasEditEnable();

    }

    public void clearAllFocus() {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.clearFocus();
        }

    }

    public void requestAllFocusable() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.requestFocus();
            childAt.setFocusable(true);
            childAt.setFocusableInTouchMode(true);
            childAt.findFocus();
        }
    }

}