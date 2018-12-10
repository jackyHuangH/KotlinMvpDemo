package com.zenchn.support.widget.enteringlayout;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * 作    者：wangr on 2017/9/6 14:47
 * 描    述：
 * 修订记录：
 */

public class EnteringLayoutHelper {

    //缓存所有的选项键值对key itemId,value itemName
    private Map<String, String> mOptionMap;

    public EnteringLayoutHelper() {
    }

    public EnteringLayoutHelper(Map<String, String> optionMap) {
        this.mOptionMap = optionMap;
    }

    public void setOptionMap(HashMap<String, String> mOptionMap) {
        this.mOptionMap = mOptionMap;
    }

    /**
     * 存入String类型键，并显示（适用于映射关系在接口（并且完成初始化）或者是输入框类型的）
     *
     * @param layout
     * @param enteringValue
     */
    public void setValue(@NonNull EnteringLayout layout, String enteringValue) {
        if (EnteringLayout.Type.input == layout.getType()) {
            layout.setRightText(enteringValue);
        } else if (EnteringLayout.Type.option == layout.getType()) {
            layout.setOptionKey(enteringValue);
            if (mOptionMap != null) {
                layout.setRightText(mOptionMap.get(enteringValue));
            }
        }
    }

    /**
     * 存入键和值，并显示
     *
     * @param layout
     * @param enteringValue
     */
    public void setValue(@NonNull EnteringLayout layout, @NonNull String enteringValue, @NonNull String enteringValueName) {
        if (EnteringLayout.Type.input == layout.getType()) {
            layout.setRightText(enteringValueName);
        } else if (EnteringLayout.Type.option == layout.getType()) {
            layout.setOptionKey(enteringValue);
            layout.setRightText(enteringValueName);
        }
    }

    /**
     * 取出键
     *
     * @param layout
     * @return
     */
    public String getValue(@NonNull EnteringLayout layout) {
        if (EnteringLayout.Type.input == layout.getType()) {
            return layout.getRightText();
        } else if (EnteringLayout.Type.option == layout.getType()) {
            return layout.getOptionKey();
        }
        return null;
    }

    /**
     * 清除值
     *
     * @param layout
     */
    public void clearValue(@NonNull EnteringLayout layout) {
        if (EnteringLayout.Type.input == layout.getType()) {
            layout.setRightText("");
        } else if (EnteringLayout.Type.option == layout.getType()) {
            layout.setOptionKey("");
            layout.setRightText("");
        }
    }

    /**
     * 设置右边文字字体颜色
     * 注意：CustomView 必须是TextView及其子类
     *
     * @param layout
     * @param color
     */
    public void setRightTextColor(@NonNull EnteringLayout layout, @ColorInt int color) {
        View customView = layout.getCustomView();
        if (!(customView instanceof TextView)) {
            throw new IllegalStateException("EnteringLayout's CustomView must be TextView or extends TextView!");
        }
        TextView tv = (TextView) customView;
        tv.setTextColor(color);
    }


    //---------------缓存相关------------------------

    /**
     * 清空内部所有enterLayout的缓存
     *
     * @param enterlayoutContainer
     */
    public void clearAllEnterlayoutCache(@NonNull ViewGroup enterlayoutContainer) {
        int count = enterlayoutContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = enterlayoutContainer.getChildAt(i);
            if (view != null && view instanceof EnteringLayout) {
                EnteringLayout target = (EnteringLayout) view;
                clearValue(target);
            }
        }
    }
}
