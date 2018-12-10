package com.zenchn.support.widget.enteringlayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.support.R;
import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.widget.factory.ViewFactory;

import java.util.Arrays;

/**
 * 作    者：wangr on 2017/6/12 18:46
 * 描    述：专注于表单数据登记 封装的控件
 * 修订记录：修正多行内容显示不全by Hzj
 */
public class EnteringLayout extends ViewGroup {

    private int mDefaultHeight;
    private int mAsteriskLeftPadding;
    private int mAsteriskRightPadding;
    private int mNextIconLeftPadding;

    private int mItemTextColor;
    private int mItemTextSize;

    private boolean mIsMustItem;
    private String mAsteriskName;
    private int mAsteriskColor;
    private int mAsteriskSize;

    private boolean mHasNextIcon;
    private View mCustomView;
    private TextView mItemTextView;
    private TextView mAsteriskView;
    private ImageView mNextIconView;

    private String mFlag;
    private Type mType = Type.input;

    public EnteringLayout(Context context) {
        this(context, null);
    }

    public EnteringLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnteringLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {

        Resources resources = context.getResources();
        int defaultTextSize = resources.getDimensionPixelSize(R.dimen.entering_layout_default_item_text_size);
        int defaultLeftPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_left_padding);
        int defaultRightPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_right_padding);
        int defaultAsteriskLeftPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_asterisk_left_padding);
        int defaultAsteriskRightPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_asterisk_right_padding);
        int defaultNextIconLeftPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_next_icon_left_padding);
        int defaultBgColor = ContextCompat.getColor(context, R.color.entering_layout_default_bg);
        int defaultAsteriskColor = ContextCompat.getColor(context, R.color.color_fb5656);
        int defaultItemTextColor = ContextCompat.getColor(context, R.color.color_282828);
        mDefaultHeight = resources.getDimensionPixelSize(R.dimen.entering_layout_default_height);

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EnteringLayout);

        String itemName = typedArray.getString(R.styleable.EnteringLayout_item_name);

        mItemTextColor = typedArray.getColor(R.styleable.EnteringLayout_item_text_color, defaultItemTextColor);
        mItemTextSize = typedArray.getDimensionPixelOffset(R.styleable.EnteringLayout_item_text_size, defaultTextSize);

        mAsteriskLeftPadding = typedArray.getDimensionPixelOffset(R.styleable.EnteringLayout_asterisk_left_padding, defaultAsteriskLeftPadding);
        mAsteriskRightPadding = typedArray.getDimensionPixelOffset(R.styleable.EnteringLayout_asterisk_right_padding, defaultAsteriskRightPadding);
        mNextIconLeftPadding = typedArray.getDimensionPixelOffset(R.styleable.EnteringLayout_next_icon_left_padding, defaultNextIconLeftPadding);
        mAsteriskName = typedArray.getString(R.styleable.EnteringLayout_asterisk_style);

        mAsteriskColor = typedArray.getColor(R.styleable.EnteringLayout_asterisk_color, defaultAsteriskColor);
        mAsteriskSize = typedArray.getDimensionPixelOffset(R.styleable.EnteringLayout_asterisk_size, defaultTextSize);


        //（0输入框/1选项卡）
        int typeValue = typedArray.getLayoutDimension(R.styleable.EnteringLayout_type, Type.input.ordinal());
        mType = Type.classifyType(typeValue);

        boolean isMustItem = typedArray.getBoolean(R.styleable.EnteringLayout_is_must_item, false);
        boolean hasNextIcon = typedArray.getBoolean(R.styleable.EnteringLayout_has_next_icon, false);
        int layoutResId = typedArray.getResourceId(R.styleable.EnteringLayout_custom_layout, 0);

        typedArray.recycle();

        setItemText(itemName);//添加左侧栏目标题
        setIsMustItem(isMustItem);//添加是否必选项
        setRightNextIcon(hasNextIcon);//设置是否包含右侧箭头
        setCustomView(layoutResId);//设置右侧自定义布局
        setPadding(defaultLeftPadding, 0, defaultRightPadding, 0);//设置默认的内边距
        setBackgroundColor(defaultBgColor);//设置默认的背景颜色
    }

    private void setIsMustItem(boolean isMustItem) {
        if (isMustItem) {
            if (mAsteriskView == null) {
                Context context = getContext();
                if (TextUtils.isEmpty(mAsteriskName)) {
                    mAsteriskName = "*";
                }
                mAsteriskView = ViewFactory.getTextView(context, mAsteriskName, mAsteriskColor, mAsteriskSize);
            }
            addView(mAsteriskView);
        } else {
            if (mAsteriskView != null) {
                removeView(mAsteriskView);
            }
            mAsteriskView = null;
        }
        mIsMustItem = isMustItem;
    }

    public boolean isMustItem() {
        return mIsMustItem;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //初始化所有子View的宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measuredWidth(widthMeasureSpec);
        int measuredHeight = measuredHeight(heightMeasureSpec);
        remeasureChildren(measuredHeight);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * 重新测量子控件高度
     *
     * @param measuredHeight
     */
    private void remeasureChildren(int measuredHeight) {
        int measuredItemTextViewWidth = 0;
        int measuredAsteriskTextViewWidth = 0;
        int measuredNextIconViewWidth = 0;

        if (mItemTextView != null) {
            measuredItemTextViewWidth = mItemTextView.getMeasuredWidth();
            mItemTextView.measure(MeasureSpec.makeMeasureSpec(measuredItemTextViewWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        }

        if (mAsteriskView != null) {
            measuredAsteriskTextViewWidth = mAsteriskView.getMeasuredWidth();
            mAsteriskView.measure(MeasureSpec.makeMeasureSpec(measuredAsteriskTextViewWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        }
        if (mNextIconView != null) {
            measuredNextIconViewWidth = mNextIconView.getMeasuredWidth();
            mNextIconView.measure(MeasureSpec.makeMeasureSpec(measuredNextIconViewWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        }
        if (mCustomView != null) {
            int contentWidth = AndroidKit.Dimens.getScreenWidth() - getPaddingLeft() - getPaddingRight();
            int measuredCustomViewMaxWidth = contentWidth - (measuredItemTextViewWidth + measuredAsteriskTextViewWidth + measuredNextIconViewWidth)
                    - (mAsteriskLeftPadding + mAsteriskRightPadding + mNextIconLeftPadding);
            int measuredCustomViewWidth = Math.min(mCustomView.getMeasuredWidth(), measuredCustomViewMaxWidth);

            mCustomView.measure(MeasureSpec.makeMeasureSpec(measuredCustomViewWidth, MeasureSpec.EXACTLY),
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
            default:
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
                result = Math.min(specSize, AndroidKit.Dimens.getScreenHeight());
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = getViewMaxHeight();
                break;
            default:
                break;
        }
        return result;
    }

    private int getViewMaxHeight() {
        int viewMaxHeight = mDefaultHeight;
        if (mItemTextView != null) {
            viewMaxHeight = Math.max(mItemTextView.getMeasuredHeight(), viewMaxHeight);
        }
        if (mAsteriskView != null) {
            viewMaxHeight = Math.max(mAsteriskView.getMeasuredHeight(), viewMaxHeight);
        }
        if (mCustomView != null) {
            viewMaxHeight = Math.max(mCustomView.getMeasuredHeight(), viewMaxHeight);
        }
        return viewMaxHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int commonViewTop = getPaddingTop();
        int commonViewRight = (r - l) - getPaddingRight();
        //取最高的子View高度作为bottom值
        int commonViewBottom = commonViewTop + getViewMaxHeight() + getPaddingBottom();

        int itemTextViewLeft = getPaddingLeft();
        int itemTextViewRight = itemTextViewLeft + mItemTextView.getMeasuredWidth();
        mItemTextView.layout(itemTextViewLeft, commonViewTop, itemTextViewRight, commonViewBottom);

        if (mAsteriskView != null) {
            int asteriskViewLeft = itemTextViewRight + mAsteriskLeftPadding;
            mAsteriskView.layout(asteriskViewLeft, commonViewTop, asteriskViewLeft + mAsteriskView.getMeasuredWidth(),
                    commonViewBottom);
        }

        if (mNextIconView != null) {
            int measuredWidth = mNextIconView.getMeasuredWidth();
            mNextIconView.layout(commonViewRight - measuredWidth, commonViewTop, commonViewRight,
                    commonViewBottom);
        }

        if (mCustomView != null) {
            int measuredWidth = mCustomView.getMeasuredWidth();
            int customViewTop = (getMeasuredHeight() - mCustomView.getMeasuredHeight()) / 2;
            int customViewRight = commonViewRight - (mNextIconView == null ? 0 : (mNextIconView.getMeasuredWidth() + mNextIconLeftPadding));
            mCustomView.layout(customViewRight - measuredWidth, customViewTop, customViewRight,
                    commonViewBottom);
        }

    }

    public View getCustomView() {
        return mCustomView;
    }

    private void setCustomView(@NonNull View view) {
        mCustomView = view;
        addView(mCustomView);
    }

    private void setCustomView(@LayoutRes int resId) {
        if (resId != 0) {
            mCustomView = LayoutInflater.from(getContext()).inflate(resId, this, false);
            addView(mCustomView);
        }
    }

    public void setItemText(String text) {
        if (mItemTextView == null) {
            mItemTextView = ViewFactory.getTextView(getContext(), text, mItemTextColor, mItemTextSize);
            addView(mItemTextView);
        }
        mItemTextView.setText(text);

    }

    public void setItemText(@StringRes int resId) {
        setItemText(getContext().getString(resId));
    }

    @NonNull
    public String getLeftText() {
        return mItemTextView != null ? mItemTextView.getText().toString() : "";
    }

    public void setRightText(String text) {
        if (mCustomView != null && mCustomView instanceof TextView) {
            ((TextView) mCustomView).setText(text);
        }
    }

    public void setRightText(@StringRes int resId) {
        setRightText(getContext().getString(resId));
    }

    @NonNull
    public String getRightText() {
        if (mCustomView != null && mCustomView instanceof TextView) {
            return ((TextView) mCustomView).getText().toString();
        }
        return "";
    }

    public void setRightNextIcon(boolean hasNextIcon) {
        if (hasNextIcon) {
            if (mNextIconView == null) {
                Context context = getContext();
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.widget_ic_next);
                mNextIconView = ViewFactory.getImageView(context, drawable);
            }
            addView(mNextIconView);
        } else {
            if (mNextIconView != null) {
                removeView(mNextIconView);
            }
            mNextIconView = null;
        }
        mHasNextIcon = hasNextIcon;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public boolean hasNextIcon() {
        return mHasNextIcon;
    }

    public String getFlag() {
        return mFlag;
    }

    public void setFlag(String flag) {
        this.mFlag = flag;
    }

//    public boolean checkValue() {
//        return !mIsMustItem || !TextUtils.isEmpty(value);
//    }


    /**********************选项值相关***********************/

    private String[] mOptionKeys;
    private String[] mOptionValues;
    private String mOptionKey;
    private String mMappingSource;

    public String getOptionValue() {
        String optionValue = null;
        if (mOptionKeys != null && mOptionValues != null && mOptionKey != null) {
            int index = Arrays.binarySearch(mOptionKeys, mOptionKey);
            if (index >= 0 && index < mOptionValues.length) {
                optionValue = mOptionValues[index];
            }
        }
        return optionValue;
    }

    public void setMappingSource(String mappingSource) {
        this.mMappingSource = mappingSource;
    }

    public String getMappingSource() {
        return mMappingSource;
    }

    public void setOptionKey(String optionKey) {
        this.mOptionKey = optionKey;
    }

    public String getOptionKey() {
        return mOptionKey;
    }

    public void setOptions(@NonNull String[] keys, @NonNull String[] values) {
        this.mOptionKeys = keys;
        this.mOptionValues = values;
    }

    public String[] getOptionKeys() {
        return mOptionKeys;
    }

    public String[] getOptionValues() {
        return mOptionValues;
    }

    public enum Type {
        /***输入类型***/
        input,
        /***选项类型***/
        option;

        public static Type classifyType(int styleValue) {
            for (Type type : values()) {
                if (type.ordinal() == styleValue) {
                    return type;
                }
            }
            return input;
        }
    }

    //----------------------------------------建造器模式------------------------------------

    public static class Builder {

        private Context mContext;

        private int mDefaultHeight;

        private String mItemText;
        private int mItemTextColor;
        private int mItemTextSize;

        private boolean mIsMustItem;
        private String mAsteriskName;
        private int mAsteriskLeftPadding;
        private int mAsteriskRightPadding;
        private int mNextIconLeftPadding;

        private int mAsteriskColor;
        private int mAsteriskSize;

        private int mDefaultLeftPadding;
        private int mDefaultRightPadding;
        private int mDefaultBgColor;

        private boolean mHasNextIcon;
        private int mCustomViewLayoutResId;
        private View mCustomView;

        private Type mType = Type.input;

        public Builder(Context context, Type type) {
            this.mContext = context;
            Resources resources = context.getResources();
            this.mItemTextColor = ContextCompat.getColor(context, R.color.color_282828);
            this.mItemTextSize = resources.getDimensionPixelSize(R.dimen.entering_layout_default_item_text_size);
            this.mDefaultLeftPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_left_padding);
            this.mDefaultRightPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_right_padding);
            this.mAsteriskSize = resources.getDimensionPixelSize(R.dimen.entering_layout_default_item_text_size);
            this.mAsteriskColor = ContextCompat.getColor(context, R.color.color_fb5656);
            this.mAsteriskLeftPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_asterisk_left_padding);
            this.mAsteriskRightPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_asterisk_right_padding);
            this.mNextIconLeftPadding = resources.getDimensionPixelSize(R.dimen.entering_layout_default_next_icon_left_padding);
            this.mDefaultBgColor = ContextCompat.getColor(context, R.color.entering_layout_default_bg);
            this.mDefaultHeight = resources.getDimensionPixelSize(R.dimen.entering_layout_default_height);
            this.mType = type;
        }

        public Builder defaultHeight(int defaultHeight) {
            this.mDefaultHeight = defaultHeight;
            return this;
        }

        public Builder itemTextColor(@ColorInt int iemTextColor) {
            this.mItemTextColor = iemTextColor;
            return this;
        }

        public Builder itemTextColorRes(@ColorRes int iemTextColorRes) {
            this.mItemTextColor = mContext.getResources().getColor(iemTextColorRes);
            return this;
        }

        public Builder itemTextSize(int itemTextSize) {
            this.mItemTextSize = itemTextSize;
            return this;
        }

        public Builder itemText(@NonNull String text) {
            this.mItemText = text;
            return this;
        }

        public Builder itemText(@StringRes int textRes) {
            this.mItemText = mContext.getString(textRes);
            return this;
        }

        public Builder isMustItem(boolean isMustItem) {
            this.mIsMustItem = isMustItem;
            return this;
        }

        public Builder asteriskName(String asteriskName) {
            this.mAsteriskName = asteriskName;
            return this;
        }

        public Builder asteriskColor(@ColorInt int asteriskColor) {
            this.mAsteriskColor = asteriskColor;
            return this;
        }

        public Builder asteriskColorRes(@ColorRes int asteriskColorRes) {
            this.mAsteriskColor = mContext.getResources().getColor(asteriskColorRes);
            return this;
        }

        public Builder asteriskSize(int asteriskSize) {
            this.mAsteriskSize = asteriskSize;
            return this;
        }

        public Builder asteriskLeftPadding(int asteriskLeftPadding) {
            this.mAsteriskLeftPadding = asteriskLeftPadding;
            return this;
        }

        public Builder asteriskRightPadding(int asteriskRightPadding) {
            this.mAsteriskRightPadding = asteriskRightPadding;
            return this;
        }

        public Builder nextIconLeftPadding(int nextIconLeftPadding) {
            this.mNextIconLeftPadding = nextIconLeftPadding;
            return this;
        }

        public Builder hasNextIcon(boolean hasNextIcon) {
            this.mHasNextIcon = hasNextIcon;
            return this;
        }

        public Builder setDefaultLeftPadding(int mDefaultLeftPadding) {
            this.mDefaultLeftPadding = mDefaultLeftPadding;
            return this;
        }

        public Builder setDefaultRightPadding(int mDefaultRightPadding) {
            this.mDefaultRightPadding = mDefaultRightPadding;
            return this;
        }

        public Builder setmDefaultBgColor(int mDefaultBgColor) {
            this.mDefaultBgColor = mDefaultBgColor;
            return this;
        }

        public Builder setCustomViewLayoutResId(@LayoutRes int mCustomViewLayoutResId) {
            this.mCustomViewLayoutResId = mCustomViewLayoutResId;
            return this;
        }

        public Builder setCustomView(@NonNull View mCustomView) {
            this.mCustomView = mCustomView;
            return this;
        }

        public EnteringLayout build() {
            EnteringLayout enteringLayout = new EnteringLayout(mContext);

            enteringLayout.mDefaultHeight = mDefaultHeight;
            enteringLayout.mAsteriskLeftPadding = mAsteriskLeftPadding;
            enteringLayout.mAsteriskRightPadding = mAsteriskRightPadding;
            enteringLayout.mNextIconLeftPadding = mNextIconLeftPadding;

            enteringLayout.mItemTextColor = mItemTextColor;
            enteringLayout.mItemTextSize = mItemTextSize;

            enteringLayout.mIsMustItem = mIsMustItem;
            enteringLayout.mAsteriskName = mAsteriskName;
            enteringLayout.mAsteriskColor = mAsteriskColor;
            enteringLayout.mAsteriskSize = mAsteriskSize;


            enteringLayout.mHasNextIcon = mHasNextIcon;
            enteringLayout.mType = mType;

            enteringLayout.setItemText(mItemText);//添加左侧栏目标题
            enteringLayout.setIsMustItem(mIsMustItem);//添加是否必选项
            if (mCustomViewLayoutResId > 0) {//优先从布局中加载
                enteringLayout.setCustomView(mCustomViewLayoutResId);//设置右侧自定义布局
            } else if (mCustomView != null) {
                enteringLayout.setCustomView(mCustomView);
            }
            enteringLayout.setRightNextIcon(mHasNextIcon);//设置是否包含右侧箭头
            enteringLayout.setPadding(mDefaultLeftPadding, 0, mDefaultRightPadding, 0);//设置默认的内边距
            enteringLayout.setBackgroundColor(mDefaultBgColor);//设置默认的背景颜色
            return enteringLayout;
        }
    }
}
