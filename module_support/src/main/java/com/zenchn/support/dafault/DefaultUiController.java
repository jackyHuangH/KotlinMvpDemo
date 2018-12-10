package com.zenchn.support.dafault;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.zenchn.support.R;
import com.zenchn.support.base.IUiController;
import com.zenchn.support.widget.tips.SuperToast;


/**
 * 作    者：wangr on 2017/4/27 14:56
 * 描    述：
 * 修订记录：
 */

public class DefaultUiController implements IUiController {

    protected Context mContext;
    private MaterialDialog mMaterialDialog;
    private MaterialDialog mProgressDialog;

    public DefaultUiController(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 显示默认 加载进度条：正在加载数据...
     */
    @Override
    public void showProgress() {
        showProgress(mContext.getString(R.string.common_library_loading));
    }

    /**
     * 显示自定义内容加载条
     *
     * @param msg
     */
    @Override
    public void showProgress(@Nullable CharSequence msg) {
        if (mMaterialDialog == null) {
            mMaterialDialog = new MaterialDialog.Builder(mContext)
                    .content(msg)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .autoDismiss(false)
                    .build();
        } else {
            mMaterialDialog.setContent(msg);
        }
        DialogHandler.safeShowDialog(mMaterialDialog);
    }

    @Override
    public void hideProgress() {
        if (mMaterialDialog != null) {
            DialogHandler.safeDismissDialog(mMaterialDialog);
        }
    }

    @Override
    public void updateProgress(int progress, int max) {
        if (mProgressDialog == null) {
            mProgressDialog = new MaterialDialog.Builder(mContext)
                    .title(R.string.common_library_loading)
                    .contentGravity(GravityEnum.CENTER)
                    .cancelable(false)
                    .canceledOnTouchOutside(false)
                    .autoDismiss(false)
                    .progress(false, max, true)
                    .build();
            DialogHandler.safeShowDialog(mProgressDialog);
        }
        if (progress < max) {
            mProgressDialog.incrementProgress(progress);
        } else {
            DialogHandler.safeDismissDialog(mProgressDialog);
            mProgressDialog = null;
        }
    }

    @Override
    public void showMessage(@NonNull CharSequence message) {
        SuperToast.showCustomMessage(mContext, message.toString());
    }

    @Override
    public void showResMessage(@StringRes int resId) {
        SuperToast.showCustomMessage(mContext, mContext.getString(resId));
    }
}
