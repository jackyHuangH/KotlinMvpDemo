package com.zenchn.support.base;

import android.support.annotation.LayoutRes;

/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：
 * 修订记录：
 */

public interface IActivity extends UiCallback{

    @LayoutRes
    int getLayoutRes();

    void initWidget();
}
