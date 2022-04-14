package com.zenchn.support.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：
 * 修订记录：
 */

public interface UiCallback {

    void showProgress();

    void showProgress(@Nullable CharSequence msg);

    void hideProgress();

    void updateProgress(int progress, int max);

    void showMessage(@NonNull CharSequence msg);

    void showResMessage(@StringRes int resId);

}
