package com.zenchn.support.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.support.R;
import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.widget.factory.ViewFactory;

/**
 * 作    者：wangr on 2017/6/12 18:46
 * 描    述：通用设置按钮
 * 修订记录：
 */
public class SettingButton extends ViewGroup {

    private int mDefaultHeight;
    private int mLeftTextColor;
    private int mRightTextColor;
    private int mLeftTextSize;
    private int mRightTextSize;
    private int mRightDrawablePadding;
    private int mRightDrawableMargin;

    private View mLeftView;
    private View mRightView;
    private ImageView mRightImageView;


    public SettingButton(Context context) {
        this(context, null);
    }

    public SettingButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingButton);

        Resources resources = getResources();
        mDefaultHeight = resources.getDimensionPixelSize(R.dimen.setting_button_default_height);

        mLeftTextColor = typedArray.getColor(R.styleable.SettingButton_setting_left_text_color, ContextCompat.getColor(context, R.color.color_282828));
        mRightTextColor = typedArray.getColor(R.styleable.SettingButton_setting_right_text_color, ContextCompat.getColor(context, R.color.color_999999));

        String leftText = typedArray.getString(R.styleable.SettingButton_setting_left_text);
        String rightText = typedArray.getString(R.styleable.SettingButton_setting_right_text);

        int defTextSize = AndroidKit.Dimens.sp2px(16);
        mLeftTextSize = typedArray.getDimensionPixelOffset(R.styleable.SettingButton_setting_left_text_size, defTextSize);
        mRightTextSize = typedArray.getDimensionPixelOffset(R.styleable.SettingButton_setting_right_text_size, defTextSize);

        int mBgColor = typedArray.getColor(R.styleable.SettingButton_setting_bg_color, ContextCompat.getColor(context, android.R.color.white));

        boolean hasNextIcon = typedArray.getBoolean(R.styleable.SettingButton_setting_has_next_icon, true);
        Drawable nextIconDrawable = typedArray.getDrawable(R.styleable.SettingButton_setting_next_icon);

        mRightDrawablePadding = typedArray.getDimensionPixelOffset(R.styleable.SettingButton_setting_right_text_drawable_padding, 0);
        mRightDrawableMargin = typedArray.getDimensionPixelOffset(R.styleable.SettingButton_setting_right_text_drawable_margin, AndroidKit.Dimens.dp2px(10));

        int leftPadding = typedArray.getDimensionPixelOffset(R.styleable.SettingButton_setting_left_padding, AndroidKit.Dimens.dp2px(15));
        int rightPadding = typedArray.getDimensionPixelOffset(R.styleable.SettingButton_setting_right_padding, AndroidKit.Dimens.dp2px(15));
        int topPadding = typedArray.getDimensionPixelOffset(R.styleable.SettingButton_setting_top_padding, 0);
        int bottomPadding = typedArray.getDimensionPixelOffset(R.styleable.SettingButton_setting_bottom_padding, 0);

        boolean settingDefault = typedArray.getBoolean(R.styleable.SettingButton_setting_default, true);

        typedArray.recycle();

        if (settingDefault) {
            leftView(ViewFactory.getTextView(context, leftText, mLeftTextColor, mLeftTextSize));

            if (!TextUtils.isEmpty(rightText))
                rightView(ViewFactory.getTextView(context, rightText, mRightTextColor, mRightTextSize));

            if (hasNextIcon) {
                if (nextIconDrawable == null) {
                    nextIconDrawable = ContextCompat.getDrawable(context, R.drawable.widget_ic_next);
                }
                rightNextIcon(nextIconDrawable);
            }
        }

        setBackgroundColor(mBgColor);
        setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measuredWidth(widthMeasureSpec);
        int measuredHeight = measuredHeight(heightMeasureSpec);
        remeasureChildren(measuredHeight);
        measuredHeight += (getPaddingTop() + getPaddingBottom());
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * 重新测量子控件高度(设置高度)
     *
     * @param measuredHeight
     */
    private void remeasureChildren(int measuredHeight) {
        if (mLeftView != null) {
            mLeftView.measure(
                    MeasureSpec.makeMeasureSpec(mLeftView.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        }
        if (mRightView != null) {
            mRightView.measure(
                    MeasureSpec.makeMeasureSpec(mRightView.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        }
        if (mRightImageView != null) {
            mRightImageView.measure(
                    MeasureSpec.makeMeasureSpec(mRightImageView.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        }
    }

    private int measuredWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {

            case MeasureSpec.EXACTLY:
                result = Math.min(specSize, AndroidKit.Dimens.getScreenWidth());
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = AndroidKit.Dimens.getScreenWidth();
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
        int viewMaxHeight = mDefaultHeight;
        if (mLeftView != null) {
            viewMaxHeight = Math.max(mLeftView.getMeasuredHeight(), viewMaxHeight);
        }
        if (mRightView != null) {
            viewMaxHeight = Math.max(mRightView.getMeasuredHeight(), viewMaxHeight);
        }
        if (mRightImageView != null) {
            viewMaxHeight = Math.max(mRightImageView.getMeasuredHeight(), viewMaxHeight);
        }
        return viewMaxHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int reviseLeft = getPaddingLeft();
        int reviseTop = getPaddingTop();
        int reviseRight = (r - l) - getPaddingRight();
        int reviseBottom = (b - t) - getPaddingBottom();

        if (mLeftView != null) {
            mLeftView.layout(reviseLeft, reviseTop, reviseLeft + mLeftView.getMeasuredWidth(), reviseTop + mLeftView.getMeasuredHeight());
        }

        int measuredWidth = 0;
        if (mRightImageView != null) {
            measuredWidth = mRightImageView.getMeasuredWidth();
            mRightImageView.layout(reviseRight - measuredWidth, reviseTop, reviseRight, reviseBottom);
        }

        if (mRightView != null) {
            if (mRightImageView == null) {
                mRightView.layout(reviseRight - mRightView.getMeasuredWidth(), reviseTop, reviseRight, reviseTop + mRightView.getMeasuredHeight());
            } else {
                int reviseRightViewRight = (reviseRight - measuredWidth) - mRightDrawableMargin;
                mRightView.layout(reviseRightViewRight - mRightView.getMeasuredWidth(), reviseTop, reviseRightViewRight, reviseTop + mRightView.getMeasuredHeight());
            }
        }
    }

    /**
     * 设置左侧自定义view
     *
     * @param customLeftView
     * @return
     */
    private SettingButton leftView(@NonNull View customLeftView) {
        if (mLeftView != null) {
            removeView(mLeftView);
        }
        addView(customLeftView);
        mLeftView = customLeftView;
        return this;
    }

    /**
     * 设置左侧图标
     *
     * @param drawableRes
     * @return
     */
    public SettingButton leftIcon(@DrawableRes int drawableRes) {
        return leftIcon(getResources().getDrawable(drawableRes));
    }

    /**
     * 设置左侧图标
     *
     * @param drawable
     * @return
     */
    public SettingButton leftIcon(@NonNull Drawable drawable) {
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
    public SettingButton leftTextColor(@ColorInt int textColor) {
        mLeftTextColor = textColor;
        if (mLeftView != null && mLeftView instanceof TextView) {
            ((TextView) mLeftView).setTextColor(textColor);
        }
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param textSize{TypedValue.COMPLEX_UNIT_SP}
     * @return
     */
    public SettingButton leftTextSize(int textSize) {
        mLeftTextSize = textSize;
        if (mLeftView != null && mLeftView instanceof TextView) {
            ((TextView) mLeftView).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    public SettingButton leftText(@Nullable String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mLeftView != null && mLeftView instanceof TextView) {
                ((TextView) mLeftView).setText(text);
            } else {
                leftView(ViewFactory.getTextView(getContext(), text, mLeftTextColor, mLeftTextSize));
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
    public SettingButton leftText(@StringRes int textRes) {
        return leftText(getResources().getString(textRes));
    }


    /**
     * 设置右侧自定义view
     *
     * @param customViewLayoutRes
     * @return
     */
    public SettingButton rightView(@LayoutRes int customViewLayoutRes) {
        LayoutInflater li = LayoutInflater.from(getContext());
        return rightView(li.inflate(customViewLayoutRes, null));
    }

    /**
     * 设置右侧自定义view
     *
     * @param customView
     * @return
     */
    public SettingButton rightView(@NonNull View customView) {
        if (customView.getParent() != null && customView.getParent() instanceof ViewGroup) {
            ((ViewGroup) customView.getParent()).removeView(customView);
        }
        if (mRightView != null) {
            removeView(mRightView);
        }
        addView(customView);
        mRightView = customView;
        return this;
    }

    /**
     * 设置右侧图标
     *
     * @param drawableRes
     * @return
     */
    public SettingButton rightIcon(@DrawableRes int drawableRes) {
        return leftIcon(getResources().getDrawable(drawableRes));
    }

    /**
     * 设置右侧图标
     *
     * @param drawable
     * @return
     */
    public SettingButton rightIcon(@NonNull Drawable drawable) {
        if (mRightView != null && mRightView instanceof ImageView) {
            ((ImageView) mRightView).setImageDrawable(drawable);
        } else {
            rightView(ViewFactory.getImageButton(getContext(), drawable));
        }
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param textColor
     * @return
     */
    public SettingButton rightTextColor(@ColorInt int textColor) {
        mRightTextColor = textColor;
        if (mRightView != null && mRightView instanceof TextView)
            ((TextView) mRightView).setTextColor(textColor);
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param textSize{TypedValue.COMPLEX_UNIT_SP}
     * @return
     */
    public SettingButton rightTextSize(int textSize) {
        mRightTextSize = textSize;
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
    public SettingButton rightText(@Nullable String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mRightView != null && mRightView instanceof TextView) {
                ((TextView) mRightView).setText(text);
            } else {
                rightView(ViewFactory.getTextView(getContext(), text, mRightTextColor, mRightTextSize));
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
    public SettingButton rightText(@StringRes int textRes) {
        return rightText(getResources().getString(textRes));
    }

    /**
     * 设置右侧next图标
     *
     * @param drawableRes
     * @return
     */
    public SettingButton rightNextIcon(@DrawableRes int drawableRes) {
        return rightNextIcon(getResources().getDrawable(drawableRes));
    }

    /**
     * 设置右侧next图标
     *
     * @param drawable
     * @return
     */
    public SettingButton rightNextIcon(@NonNull Drawable drawable) {
        if (mRightImageView != null) {
            mRightImageView.setImageDrawable(drawable);
        } else {
            rightNextImageView(ViewFactory.getImageButton(getContext(), drawable));
        }
        return this;
    }

    /**
     * 设置右侧next自定义view
     *
     * @param imageView
     * @return
     */
    private SettingButton rightNextImageView(@NonNull ImageView imageView) {
        if (mRightImageView != null) {
            removeView(mRightImageView);
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(mRightDrawablePadding, 0, 0, 0);
        addView(imageView);
        mRightImageView = imageView;
        return this;
    }

    @Nullable
    public String getLeftText() {
        if (mLeftView != null && mLeftView instanceof TextView) {
            return ((TextView) mLeftView).getText().toString();
        } else {
            return null;
        }
    }

    @Nullable
    public String getRightText() {
        if (mRightView != null && mRightView instanceof TextView) {
            return ((TextView) mRightView).getText().toString();
        } else {
            return null;
        }
    }

}
