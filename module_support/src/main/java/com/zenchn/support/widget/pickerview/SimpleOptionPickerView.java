package com.zenchn.support.widget.pickerview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.WheelOptions;
import com.contrarywind.view.WheelView;
import com.zenchn.support.R;

import java.util.List;


/**
 * 作    者：wangr on 2017/9/5 20:39
 * 描    述：
 * 修订记录：
 */

public class SimpleOptionPickerView extends BasePickerView implements View.OnClickListener {
    private static final int PICKER_VIEW_BTN_COLOR_NORMAL = 0xFF057dff;
    private static final int PICKER_VIEW_BG_COLOR_TITLE = 0xFFf5f5f5;
    private static final int PICKER_VIEW_COLOR_TITLE = 0xFF000000;
    private static final int PICKER_VIEW_BG_COLOR_DEFAULT = 0xFFFFFFFF;

    private WheelOptions<String> wheelOptions;
    private int layoutRes;
    private CustomListener customListener;
    private Button btnSubmit, btnCancel; //确定、取消按钮
    private TextView tvTitle;
    private RelativeLayout rv_top_bar;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private String Str_Submit;//确定按钮文字
    private String Str_Cancel;//取消按钮文字
    private String Str_Title;//标题文字

    private int Color_Submit;//确定按钮颜色
    private int Color_Cancel;//取消按钮颜色
    private int Color_Title;//标题颜色

    private int Color_Background_Wheel;//滚轮背景颜色
    private int Color_Background_Title;//标题背景颜色

    private int Size_Submit_Cancel;//确定取消按钮大小
    private int Size_Title;//标题文字大小
    private int Size_Content;//内容文字大小

    private int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
    private int textColorCenter = 0xFF2a2a2a; //分割线之间的文字颜色
    private int dividerColor = 0xFFd5d5d5; //分割线的颜色
    private int backgroundId = -1; //显示时的外部背景色颜色,默认是灰色

    // 条目间距倍数 默认1.6
    private float lineSpacingMultiplier = 1.6F;
    private boolean isDialog;//是否是对话框模式

    private boolean cancelable;//是否能取消
    private boolean linkage;//是否联动

    private boolean isCenterLabel;//是否只显示中间的label

    private String label1;//单位
    private String label2;
    private String label3;

    private boolean cyclic1;//是否循环
    private boolean cyclic2;
    private boolean cyclic3;

    private Typeface font;//字体样式

    private int option1;//默认选中项
    private int option2;
    private int option3;
    private WheelView.DividerType dividerType;//分隔线类型

    private String mSource;

    private OnOptionsSelectListener optionsSelectListener;

    //构造方法
    public SimpleOptionPickerView(Builder builder) {
        super(builder.context);
        //初始化配置类
        mPickerOptions = new PickerOptions(PickerOptions.TYPE_PICKER_OPTIONS);
        mPickerOptions.context = builder.context;

        this.optionsSelectListener = builder.optionsSelectListener;
        this.Str_Submit = builder.Str_Submit;
        this.Str_Cancel = builder.Str_Cancel;
        this.Str_Title = builder.Str_Title;

        this.Color_Submit = builder.Color_Submit;
        this.Color_Cancel = builder.Color_Cancel;
        this.Color_Title = builder.Color_Title;
        this.Color_Background_Wheel = builder.Color_Background_Wheel;
        this.Color_Background_Title = builder.Color_Background_Title;

        this.Size_Submit_Cancel = builder.Size_Submit_Cancel;
        this.Size_Title = builder.Size_Title;
        this.Size_Content = builder.Size_Content;

        this.cyclic1 = builder.cyclic1;
        this.cyclic2 = builder.cyclic2;
        this.cyclic3 = builder.cyclic3;

        this.cancelable = builder.cancelable;
        this.linkage = builder.linkage;
        this.isCenterLabel = builder.isCenterLabel;

        this.label1 = builder.label1;
        this.label2 = builder.label2;
        this.label3 = builder.label3;

        this.font = builder.font;


        this.option1 = builder.option1;
        this.option2 = builder.option2;
        this.option3 = builder.option3;
        this.textColorCenter = builder.textColorCenter;
        this.textColorOut = builder.textColorOut;
        this.dividerColor = builder.dividerColor;
        this.lineSpacingMultiplier = builder.lineSpacingMultiplier;
        this.customListener = builder.customListener;
        this.layoutRes = builder.layoutRes;
        this.isDialog = builder.isDialog;
        this.dividerType = builder.dividerType;
        this.backgroundId = builder.backgroundId;

        mPickerOptions.decorView = builder.decorView;
        mPickerOptions.cancelable = builder.cancelable;
        initView(builder.context);
    }


    //建造器
    public static class Builder {

        private int layoutRes = R.layout.pickerview_options;
        private CustomListener customListener;
        private Context context;
        private OnOptionsSelectListener optionsSelectListener;

        private String Str_Submit;//确定按钮文字
        private String Str_Cancel;//取消按钮文字
        private String Str_Title;//标题文字

        private int Color_Submit;//确定按钮颜色
        private int Color_Cancel;//取消按钮颜色
        private int Color_Title;//标题颜色

        private int Color_Background_Wheel;//滚轮背景颜色
        private int Color_Background_Title;//标题背景颜色

        private int Size_Submit_Cancel = 17;//确定取消按钮大小
        private int Size_Title = 18;//标题文字大小
        private int Size_Content = 18;//内容文字大小

        private boolean cancelable = true;//是否能取消
        private boolean linkage = true;//是否联动
        private boolean isCenterLabel = true;//是否只显示中间的label

        private int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
        private int textColorCenter = 0xFF2a2a2a; //分割线之间的文字颜色
        private int dividerColor = 0xFFd5d5d5; //分割线的颜色
        private int backgroundId = -1; //显示时的外部背景色颜色,默认是灰色
        private ViewGroup decorView;//显示pickerview的根View,默认是activity的根view
        // 条目间距倍数 默认1.6
        private float lineSpacingMultiplier = 1.6F;
        private boolean isDialog;//是否是对话框模式

        private String label1;
        private String label2;
        private String label3;

        private boolean cyclic1 = false;//是否循环，默认否
        private boolean cyclic2 = false;
        private boolean cyclic3 = false;

        private Typeface font;

        private int option1;//默认选中项
        private int option2;
        private int option3;

        private WheelView.DividerType dividerType;//分隔线类型

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setOptionsSelectListener(OnOptionsSelectListener optionsSelectListener) {
            this.optionsSelectListener = optionsSelectListener;
            return this;
        }

        public Builder setSubmitText(String Str_Cancel) {
            this.Str_Submit = Str_Cancel;
            return this;
        }

        public Builder setCancelText(String Str_Cancel) {
            this.Str_Cancel = Str_Cancel;
            return this;
        }

        public Builder setTitleText(String Str_Title) {
            this.Str_Title = Str_Title;
            return this;
        }

        public Builder isDialog(boolean isDialog) {
            this.isDialog = isDialog;
            return this;
        }

        public Builder setSubmitColor(int Color_Submit) {
            this.Color_Submit = Color_Submit;
            return this;
        }

        public Builder setCancelColor(int Color_Cancel) {
            this.Color_Cancel = Color_Cancel;
            return this;
        }

        /**
         * 显示时的外部背景色颜色,默认是灰色
         *
         * @param backgroundId
         * @return
         */
        public Builder setBackgroundId(int backgroundId) {
            this.backgroundId = backgroundId;
            return this;
        }

        /**
         * 必须是viewgroup
         * 设置要将pickerview显示到的容器
         *
         * @param decorView
         * @return
         */
        public Builder setDecorView(ViewGroup decorView) {
            this.decorView = decorView;
            return this;
        }

        public Builder setLayoutRes(@LayoutRes int res, CustomListener listener) {
            this.layoutRes = res;
            this.customListener = listener;
            return this;
        }

        public Builder setBgColor(int Color_Background_Wheel) {
            this.Color_Background_Wheel = Color_Background_Wheel;
            return this;
        }

        public Builder setTitleBgColor(int Color_Background_Title) {
            this.Color_Background_Title = Color_Background_Title;
            return this;
        }

        public Builder setTitleColor(int Color_Title) {
            this.Color_Title = Color_Title;
            return this;
        }

        public Builder setSubCalSize(int Size_Submit_Cancel) {
            this.Size_Submit_Cancel = Size_Submit_Cancel;
            return this;
        }

        public Builder setTitleSize(int Size_Title) {
            this.Size_Title = Size_Title;
            return this;
        }

        public Builder setContentTextSize(int Size_Content) {
            this.Size_Content = Size_Content;
            return this;
        }


        public Builder setOutSideCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        /**
         * 此方法已废弃
         * 不联动的情况下，请调用 setNPicker 方法。
         */
        @Deprecated
        public Builder setLinkage(boolean linkage) {
            this.linkage = linkage;
            return this;
        }

        public Builder setLabels(String label1, String label2, String label3) {
            this.label1 = label1;
            this.label2 = label2;
            this.label3 = label3;
            return this;
        }

        /**
         * 设置间距倍数,但是只能在1.2-2.0f之间
         *
         * @param lineSpacingMultiplier
         */
        public Builder setLineSpacingMultiplier(float lineSpacingMultiplier) {
            this.lineSpacingMultiplier = lineSpacingMultiplier;
            return this;
        }

        /**
         * 设置分割线的颜色
         *
         * @param dividerColor
         */
        public Builder setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        /**
         * 设置分割线的类型
         *
         * @param dividerType
         */
        public Builder setDividerType(WheelView.DividerType dividerType) {
            this.dividerType = dividerType;
            return this;
        }

        /**
         * 设置分割线之间的文字的颜色
         *
         * @param textColorCenter
         */
        public Builder setTextColorCenter(int textColorCenter) {
            this.textColorCenter = textColorCenter;
            return this;
        }

        /**
         * 设置分割线以外文字的颜色
         *
         * @param textColorOut
         */
        public Builder setTextColorOut(int textColorOut) {
            this.textColorOut = textColorOut;
            return this;
        }

        public Builder setTypeface(Typeface font) {
            this.font = font;
            return this;
        }

        public Builder setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
            this.cyclic1 = cyclic1;
            this.cyclic2 = cyclic2;
            this.cyclic3 = cyclic3;
            return this;
        }

        public Builder setSelectOptions(int option1) {
            this.option1 = option1;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2) {
            this.option1 = option1;
            this.option2 = option2;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2, int option3) {
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel) {
            this.isCenterLabel = isCenterLabel;
            return this;
        }

        public SimpleOptionPickerView build() {
            return new SimpleOptionPickerView(this);
        }
    }


    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
        initEvents();
        if (customListener == null) {
            LayoutInflater.from(context).inflate(layoutRes, contentContainer);

            //顶部标题
            tvTitle = (TextView) findViewById(R.id.tvTitle);
            rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);

            //确定和取消按钮
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            btnCancel = (Button) findViewById(R.id.btnCancel);

            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);
            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            //设置文字
            btnSubmit.setText(TextUtils.isEmpty(Str_Submit) ? context.getResources().getString(R.string.pickerview_submit) : Str_Submit);
            btnCancel.setText(TextUtils.isEmpty(Str_Cancel) ? context.getResources().getString(R.string.pickerview_cancel) : Str_Cancel);
            tvTitle.setText(TextUtils.isEmpty(Str_Title) ? "" : Str_Title);//默认为空

            //设置color
            btnSubmit.setTextColor(Color_Submit == 0 ? PICKER_VIEW_BTN_COLOR_NORMAL : Color_Submit);
            btnCancel.setTextColor(Color_Cancel == 0 ? PICKER_VIEW_BTN_COLOR_NORMAL : Color_Cancel);
            tvTitle.setTextColor(Color_Title == 0 ? PICKER_VIEW_COLOR_TITLE : Color_Title);
            rv_top_bar.setBackgroundColor(Color_Background_Title == 0 ? PICKER_VIEW_BG_COLOR_TITLE : Color_Background_Title);

            //设置文字大小
            btnSubmit.setTextSize(Size_Submit_Cancel);
            btnCancel.setTextSize(Size_Submit_Cancel);
            tvTitle.setTextSize(Size_Title);
            tvTitle.setText(Str_Title);
        } else {
            customListener.customLayout(LayoutInflater.from(context).inflate(layoutRes, contentContainer));
        }

        // ----滚轮布局
        final LinearLayout optionsPicker = (LinearLayout) findViewById(R.id.optionspicker);
        optionsPicker.setBackgroundColor(Color_Background_Wheel == 0 ? PICKER_VIEW_BG_COLOR_DEFAULT : Color_Background_Wheel);

        wheelOptions = new WheelOptions<>(optionsPicker, linkage);
        wheelOptions.setTextContentSize(Size_Content);
        wheelOptions.setLabels(label1, label2, label3);
        wheelOptions.setCyclic(cyclic1, cyclic2, cyclic3);
        wheelOptions.setTypeface(font);

        setOutSideCancelable(cancelable);

        if (tvTitle != null) {
            tvTitle.setText(Str_Title);
        }

        wheelOptions.setDividerColor(dividerColor);
        wheelOptions.setDividerType(dividerType);
        wheelOptions.setLineSpacingMultiplier(lineSpacingMultiplier);
        wheelOptions.setTextColorOut(textColorOut);
        wheelOptions.setTextColorCenter(textColorCenter);
        wheelOptions.isCenterLabel(isCenterLabel);

    }


    /**
     * 设置默认选中项
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        this.option1 = option1;
        SetCurrentItems();
    }


    public void setSelectOptions(int option1, int option2) {
        this.option1 = option1;
        this.option2 = option2;
        SetCurrentItems();
    }

    public void setSelectOptions(int option1, int option2, int option3) {
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        SetCurrentItems();
    }

    private void SetCurrentItems() {
        if (wheelOptions != null) {
            wheelOptions.setCurrentItems(option1, option2, option3);
        }
    }

    /**
     * 自定义解析数据
     *
     * @param source
     * @param converter
     */
    public void setPicker(String source, IOptionConverter converter) {
        this.mSource = source;
        List<String> optionsItems = converter.convertOptions1(source);
        List<List<String>> options2Items = converter.convertOptions2(source);
        List<List<List<String>>> options3Items = converter.convertOptions3(source);
        this.setPicker(optionsItems, options2Items, options3Items);
    }

    public void setPicker(List<String> optionsItems) {
        this.setPicker(optionsItems, null, null);
    }

    public void setPicker(List<String> options1Items, List<List<String>> options2Items) {
        this.setPicker(options1Items, options2Items, null);
    }

    public void setPicker(List<String> options1Items,
                          List<List<String>> options2Items,
                          List<List<List<String>>> options3Items) {

        wheelOptions.setPicker(options1Items, options2Items, options3Items);
        SetCurrentItems();
    }


    //不联动情况下调用
    public void setNPicker(List<String> options1Items,
                           List<String> options2Items,
                           List<String> options3Items) {

        wheelOptions.setNPicker(options1Items, options2Items, options3Items);
        SetCurrentItems();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    //抽离接口回调的方法
    public void returnData() {
        if (optionsSelectListener != null) {
            int[] optionsCurrentItems = wheelOptions.getCurrentItems();
            optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
        }

    }

    public interface OnOptionsSelectListener {

        void onOptionsSelect(int options1, int options2, int options3);

    }

    @Override
    public boolean isDialog() {
        return isDialog;
    }
}
