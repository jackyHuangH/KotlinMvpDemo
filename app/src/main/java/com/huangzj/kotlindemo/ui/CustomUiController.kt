package com.huangzj.kotlindemo.ui

import android.content.Context
import android.view.View
import com.zenchn.support.dafault.DefaultUiController
import com.zenchn.support.widget.tips.SuperToast

/**
 * @author:Hzj
 * @date  :2018/10/30/030
 * desc  ：
 * record：
 */
abstract class CustomUiController(context: Context) : DefaultUiController(context) {

    override fun showMessage(message: CharSequence) {
        SuperToast.showDefaultMessage(mContext, message.toString())
    }

    override fun showResMessage(resId: Int) {
        showMessage(mContext.getString(resId))
    }

    protected abstract fun getSnackBarParentView():View
}