package com.zenchn.support.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import com.zenchn.support.dafault.DefaultUiController;
import com.zenchn.support.widget.tips.SuperSnackBar;


/**
 * 作    者：wangr on 2017/4/27 14:56
 * 描    述：
 * 修订记录：
 */

public abstract class AbstractUiController extends DefaultUiController {

    public AbstractUiController(@NonNull Context mContext) {
        super(mContext);
    }

    @Override
    public void showMessage(@NonNull CharSequence message) {
        View snackBarParentView = getSnackBarParentView();
        if (snackBarParentView != null) {
            SuperSnackBar.createShortSnackbar(snackBarParentView, message, SuperSnackBar.Info).show();
        }
    }

    @Override
    public void showResMessage(@StringRes int resId) {
        showMessage(mContext.getString(resId));
    }

    /**
     * 指定SnackBar的寄主
     *
     * @return
     */
    protected abstract View getSnackBarParentView();
}
