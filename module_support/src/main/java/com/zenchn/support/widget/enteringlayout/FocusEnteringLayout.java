package com.zenchn.support.widget.enteringlayout;

import android.content.Context;
import android.util.AttributeSet;


/**
 * 作    者：wangr on 2017/9/17 11:12
 * 描    述：非编辑状态默认可以点击
 * 修订记录：
 */
public class FocusEnteringLayout extends EnteringLayout implements EditLinearLayout.EditEnableLayout {

    public FocusEnteringLayout(Context context) {
        super(context);
    }

    public FocusEnteringLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusEnteringLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean hasEditEnable() {
        return true;
    }

}
