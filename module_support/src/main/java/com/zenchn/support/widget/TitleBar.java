package com.zenchn.support.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zenchn.support.R;
import com.zenchn.support.kit.Android;
import com.zenchn.support.widget.factory.ViewFactory;

/**
 * 作    者：wangr on 2017/6/8 18:31
 * 描    述：
 * 修订记录：
 */
public class TitleBar extends ViewGroup {

    private static final int DEFAULT_HEIGHT = Android.Dimens.dp2px(45);

    private OnLeftClickListener mLeftClickListener;
    private OnRightClickListener mRightClickListener;

    private View mSeparateView;

    private int mTitleBarCenterTextColor;
    private int mTitleBarCenterTextSize;
    private int mTitleBarRightTextColor;
    private int mTitleBarRightTextSize;
    private int mTitleBarLeftTextColor;
    private int mTitleBarLeftTextSize;

    private View mLeftView;
    private View mRightView;
    private View mCenterView;

    private int mTitleBarTopAndBottomMargin;
    private int mTitleBarRightClickPadding;
    private int mTitleBarLeftClickPadding;
    private int mTitleBarSeparateHeight;
    private int mTitleBarSeparateColor;


    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        Resources resources = context.getResources();

        Drawable titleBarLeftIconDrawable = typedArray.getDrawable(R.styleable.TitleBar_title_bar_left_icon);

        String titleBarCenterText = typedArray.getString(R.styleable.TitleBar_title_bar_center_text);
        mTitleBarCenterTextColor = typedArray.getColor(R.styleable.TitleBar_title_bar_center_text_color, ContextCompat.getColor(context, R.color.color_282828));
        mTitleBarCenterTextSize = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_title_bar_center_text_size, Android.Dimens.sp2px(19));

        mTitleBarLeftTextColor = typedArray.getColor(R.styleable.TitleBar_title_bar_left_text_color, ContextCompat.getColor(context, R.color.color_282828));
        mTitleBarLeftTextSize = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_title_bar_left_text_size, Android.Dimens.sp2px(16));

        mTitleBarRightTextColor = typedArray.getColor(R.styleable.TitleBar_title_bar_right_text_color, ContextCompat.getColor(context, R.color.color_282828));
        mTitleBarRightTextSize = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_title_bar_right_text_size, Android.Dimens.sp2px(16));
        String titleBarRightText = typedArray.getString(R.styleable.TitleBar_title_bar_right_text);

        mTitleBarTopAndBottomMargin = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_title_bar_top_and_bottom_margin, Android.Dimens.dp2px(10));
        mTitleBarLeftClickPadding = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_title_bar_left_click_padding, Android.Dimens.dp2px(20));
        mTitleBarRightClickPadding = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_title_bar_right_click_padding, Android.Dimens.dp2px(15));

        int titleBarDefaultBgColor = typedArray.getColor(R.styleable.TitleBar_title_bar_default_bg_color, ContextCompat.getColor(context, android.R.color.white));

        boolean titleBarDefault = typedArray.getBoolean(R.styleable.TitleBar_title_bar_default, true);

        mTitleBarSeparateColor = typedArray.getColor(R.styleable.TitleBar_title_bar_separate_color, ContextCompat.getColor(context, R.color.color_cfcfcf));
        mTitleBarSeparateHeight = typedArray.getDimensionPixelOffset(R.styleable.TitleBar_title_bar_separate_height, Android.Dimens.dp2px(1));

        typedArray.recycle();


        mSeparateView = getSeparateView(context, mTitleBarSeparateColor, mTitleBarSeparateHeight);
        setSeparateView(mSeparateView);

        if (titleBarDefault) {
            if (titleBarLeftIconDrawable == null) {
                titleBarLeftIconDrawable = ContextCompat.getDrawable(context, R.drawable.widget_ic_back);
            }
            leftView(ViewFactory.getImageButton(context, titleBarLeftIconDrawable));
            titleText(titleBarCenterText);
            if (!TextUtils.isEmpty(titleBarRightText))
                rightText(titleBarRightText);
        }

        setBackgroundColor(titleBarDefaultBgColor);
    }

    private void addLeftClickListener() {
        if (mLeftView != null)
            mLeftView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLeftClickListener != null)
                        mLeftClickListener.onLeftViewClick(v);
                }
            });
    }

    private void addRightClickListener() {
        if (mRightView != null)
            mRightView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRightClickListener != null)
                        mRightClickListener.onRightViewClick(v);
                }
            });
    }

    private View getSeparateView(@NonNull Context context, @ColorInt int titleBarSeparateColor, int mTitleBarSeparateHeight) {
        View view = new View(context);
        view.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, mTitleBarSeparateHeight));
        view.setBackgroundColor(titleBarSeparateColor);
        return view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = measuredHeight(heightMeasureSpec);
        int measuredWidth = measuredWidth(widthMeasureSpec);
        remeasureChildren(measuredHeight);
        if (mSeparateView != null)
            measuredHeight += mSeparateView.getMeasuredHeight();
        measuredHeight += (getPaddingTop() + getPaddingBottom());
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * 重新测量子控件高度
     *
     * @param measuredHeight
     */
    private void remeasureChildren(int measuredHeight) {
        if (mLeftView != null)
            mLeftView.measure(
                    MeasureSpec.makeMeasureSpec(mLeftView.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        if (mRightView != null)
            mRightView.measure(
                    MeasureSpec.makeMeasureSpec(mRightView.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
    }

    private int measuredWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {

            case MeasureSpec.EXACTLY:
                result = Math.min(specSize, Android.Dimens.getScreenWidth());
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = Android.Dimens.getScreenWidth();
                break;
        }
        return result;
    }

    private int measuredHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {

            case MeasureSpec.EXACTLY:
                result = specSize;
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = getViewMaxHeight();
                break;
        }
        return result;
    }

    private int getViewMaxHeight() {
        int viewMaxHeight = DEFAULT_HEIGHT;
        if (mCenterView != null)
            viewMaxHeight = Math.max(mCenterView.getMeasuredHeight() + mTitleBarTopAndBottomMargin * 2, viewMaxHeight);
        if (mLeftView != null)
            viewMaxHeight = Math.max(mLeftView.getMeasuredHeight(), viewMaxHeight);
        if (mRightView != null)
            viewMaxHeight = Math.max(mRightView.getMeasuredHeight(), viewMaxHeight);
        return viewMaxHeight;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int reviseLeft = getPaddingLeft();
        int reviseTop = getPaddingTop();
        int reviseRight = (r - l) - getPaddingRight();
        int reviseBottom = (b - t) - getPaddingBottom();

        int separateViewHeight = mSeparateView == null ? 0 : mSeparateView.getMeasuredHeight();//分隔线高度
        int reviseContentHeight = (reviseBottom - reviseTop) - separateViewHeight;//内容区高度
        int reviseContentWidth = (reviseRight - reviseLeft);//内容区宽度
        int reviseContentBottom = (reviseBottom - separateViewHeight);//内容区底部位置


        if (mLeftView != null) {
            mLeftView.layout(reviseLeft, reviseTop, reviseLeft + mLeftView.getMeasuredWidth(), reviseTop + mLeftView.getMeasuredHeight());
        }

        if (mCenterView != null) {
            int reviseCenterLeft = reviseLeft + (reviseContentWidth - mCenterView.getMeasuredWidth()) / 2;
            int reviseCenterTop = reviseTop + (reviseContentHeight - mCenterView.getMeasuredHeight()) / 2;
            mCenterView.layout(reviseCenterLeft, reviseCenterTop, reviseCenterLeft + mCenterView.getMeasuredWidth(), reviseCenterTop + mCenterView.getMeasuredHeight());
        }

        if (mRightView != null) {
            mRightView.layout(reviseRight - mRightView.getMeasuredWidth(), reviseTop, reviseRight, reviseTop + mRightView.getMeasuredHeight());
        }

        if (mSeparateView != null) {
            mSeparateView.layout(reviseLeft, reviseContentBottom, reviseRight, reviseBottom);
        }

    }

    /**
     * 设置view可见性
     *
     * @param isLeftVisible
     * @return
     */

    public TitleBar leftVisible(boolean isLeftVisible) {
        if (mLeftView != null)
            mLeftView.setVisibility(isLeftVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置view可见性
     *
     * @param isRightVisible
     * @return
     */
    public TitleBar rightVisible(boolean isRightVisible) {
        if (mRightView != null)
            mRightView.setVisibility(isRightVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置view可见性
     *
     * @param isCenterVisible
     * @return
     */
    public TitleBar centerVisible(boolean isCenterVisible) {
        if (mCenterView != null)
            mCenterView.setVisibility(isCenterVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置标题栏自定义view
     *
     * @param customTitleView
     * @return
     */
    public TitleBar titleView(@NonNull View customTitleView) {
        if (mCenterView != null)
            removeView(mCenterView);
        addView(customTitleView);
        mCenterView = customTitleView;
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public TitleBar titleText(@Nullable String title) {
        if (mCenterView != null && mCenterView instanceof TextView) {
            ((TextView) mCenterView).setText(title);
        } else {
            titleView(ViewFactory.getTextView(getContext(), title, mTitleBarCenterTextColor, mTitleBarCenterTextSize));
        }
        return this;
    }

    /**
     * 设置标题
     *
     * @param titleRes
     */
    public TitleBar titleText(@StringRes int titleRes) {
        return titleText(getResources().getString(titleRes));
    }

    /**
     * 设置左侧自定义view
     *
     * @param customLeftView
     * @return
     */
    private TitleBar leftView(@NonNull View customLeftView) {
        if (mLeftView != null)
            removeView(mLeftView);
        customLeftView.setPadding(mTitleBarLeftClickPadding, 0, mTitleBarLeftClickPadding, 0);
        addView(customLeftView);
        mLeftView = customLeftView;
        addLeftClickListener();
        return this;
    }

    /**
     * 设置左侧图标
     *
     * @param drawableRes
     * @return
     */
    public TitleBar leftIcon(@DrawableRes int drawableRes) {
        return leftIcon(getResources().getDrawable(drawableRes));
    }

    /**
     * 设置左侧图标
     *
     * @param drawable
     * @return
     */
    public TitleBar leftIcon(@NonNull Drawable drawable) {
        if (mLeftView != null && mLeftView instanceof ImageView) {
            ((ImageView) mLeftView).setImageDrawable(drawable);
        } else {
            leftView(ViewFactory.getImageButton(getContext(), drawable));
        }
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param textColor
     * @return
     */
    public TitleBar leftTextColor(@ColorInt int textColor) {
        mTitleBarLeftTextColor = textColor;
        if (mLeftView != null && mLeftView instanceof TextView)
            ((TextView) mLeftView).setTextColor(textColor);
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param textSize{TypedValue.COMPLEX_UNIT_SP}
     * @return
     */
    public TitleBar leftTextSize(int textSize) {
        mTitleBarLeftTextSize = textSize;
        if (mLeftView != null && mLeftView instanceof TextView)
            ((TextView) mLeftView).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    public TitleBar leftText(@Nullable String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mLeftView != null && mLeftView instanceof TextView) {
                ((TextView) mLeftView).setText(text);
            } else {
                leftView(ViewFactory.getTextView(getContext(), text, mTitleBarLeftTextColor, mTitleBarLeftTextSize));
            }
        }
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param textRes
     * @return
     */
    public TitleBar leftText(@StringRes int textRes) {
        return leftText(getResources().getString(textRes));
    }

    /**
     * 设置右侧自定义view
     *
     * @param customRightView
     * @return
     */
    public TitleBar rightView(@NonNull View customRightView) {
        if (mRightView != null)
            removeView(mRightView);
        customRightView.setPadding(mTitleBarRightClickPadding, 0, mTitleBarRightClickPadding, 0);
        addView(customRightView);
        mRightView = customRightView;
        addRightClickListener();
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param textColor
     * @return
     */
    public TitleBar rightTextColor(@ColorInt int textColor) {
        mTitleBarRightTextColor = textColor;
        if (mRightView != null && mRightView instanceof TextView)
            ((TextView) mRightView).setTextColor(textColor);
        return this;
    }

    /**
     * 设置右侧文字{TypedValue.COMPLEX_UNIT_SP}
     *
     * @param textSize
     * @return
     */
    public TitleBar rightTextSize(int textSize) {
        mTitleBarRightTextSize = textSize;
        if (mRightView != null && mRightView instanceof TextView)
            ((TextView) mRightView).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    public TitleBar rightText(@Nullable String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mRightView != null && mRightView instanceof TextView) {
                ((TextView) mRightView).setText(text);
            } else {
                rightView(ViewFactory.getTextView(getContext(), text, mTitleBarRightTextColor, mTitleBarRightTextSize));
            }
        }
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param textRes
     * @return
     */
    public TitleBar rightText(@StringRes int textRes) {
        return rightText(getResources().getString(textRes));
    }

    /**
     * 设置右侧图标
     *
     * @param drawableRes
     * @return
     */
    public TitleBar rightIcon(@DrawableRes int drawableRes) {
        return rightIcon(getResources().getDrawable(drawableRes));
    }

    /**
     * 设置右侧图标
     *
     * @param drawable
     * @return
     */
    public TitleBar rightIcon(@NonNull Drawable drawable) {
        if (mRightView != null && mRightView instanceof ImageView) {
            ((ImageView) mRightView).setImageDrawable(drawable);
        } else {
            rightView(ViewFactory.getImageButton(getContext(), drawable));
        }
        return this;
    }

    public TitleBar setTitleBarTopAndBottomMargin(int topAndBottomMargin) {
        if (mTitleBarTopAndBottomMargin != topAndBottomMargin) {
            this.mTitleBarTopAndBottomMargin = topAndBottomMargin;
            requestLayout();
        }
        return this;
    }

    public TitleBar setTitleBarRightClickPadding(int rightClickPadding) {
        if (mTitleBarRightClickPadding != rightClickPadding) {
            this.mTitleBarRightClickPadding = rightClickPadding;
            requestLayout();
        }
        return this;
    }

    public TitleBar setTitleBarLeftClickPadding(int leftClickPadding) {
        if (mTitleBarLeftClickPadding != leftClickPadding) {
            this.mTitleBarLeftClickPadding = leftClickPadding;
            requestLayout();
        }
        return this;
    }

    /**
     * 设置左侧按钮监听
     *
     * @param listener
     * @return
     */
    public TitleBar setOnLeftClickListener(@Nullable OnLeftClickListener listener) {
        mLeftClickListener = listener;
        return this;
    }

    /**
     * 设置右侧按钮监听
     *
     * @param listener
     * @return
     */
    public TitleBar setOnRightClickListener(@Nullable OnRightClickListener listener) {
        mRightClickListener = listener;
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param colorRes
     * @return
     */
    public TitleBar setBackground(@ColorRes int colorRes) {
        setBackgroundColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置背景
     *
     * @param drawableRes
     * @return
     */
    public TitleBar setBackgroundDrawable(@DrawableRes int drawableRes) {
        setBackgroundDrawable(getResources().getDrawable(drawableRes));
        return this;
    }

    /**
     * 设置分割线view
     *
     * @param view
     * @return
     */
    public void setSeparateView(@NonNull View view) {
        addView(view);
        mSeparateView = view;
    }

    /**
     * 设置分割线颜色
     *
     * @param colorRes
     * @return
     */
    public TitleBar setSeparateColor(@ColorRes int colorRes) {
        this.mTitleBarSeparateColor = getResources().getColor(colorRes);
        if (mSeparateView != null)
            mSeparateView.setBackgroundColor(mTitleBarSeparateColor);
        return this;
    }

    /**
     * 设置分割线高度{TypedValue.COMPLEX_UNIT_DP}
     *
     * @param separateHeight()
     * @return
     */
    public TitleBar setSeparateHeight(int separateHeight) {
        this.mTitleBarSeparateHeight = separateHeight;
        if (mSeparateView != null) {
            mSeparateView.getLayoutParams().height = Android.Dimens.dp2px(separateHeight);
            requestLayout();
        }
        return this;
    }

    /**
     * 设置分割线背景
     *
     * @param drawableRes
     * @return
     */
    public TitleBar setSeparateDrawable(@DrawableRes int drawableRes) {
        if (mSeparateView != null)
            mSeparateView.setBackgroundResource(drawableRes);
        return this;
    }

    /**
     * 获取标题左边view
     *
     * @return
     */
    public View getTitleBarLeftView() {
        return mLeftView;
    }

    /**
     * 获取标题栏右边view
     *
     * @return
     */
    public View getTitleBarRightView() {
        return mRightView;
    }

    /**
     * 获取标题栏中间view
     *
     * @return
     */
    public View getTitleBarCenterView() {
        return mCenterView;
    }

    public interface OnLeftClickListener {

        void onLeftViewClick(View v);
    }

    public interface OnRightClickListener {

        void onRightViewClick(View v);
    }

}
