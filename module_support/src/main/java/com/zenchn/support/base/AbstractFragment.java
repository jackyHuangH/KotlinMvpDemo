package com.zenchn.support.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zenchn.support.dafault.DefaultUiController;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：
 * 修订记录：
 */

public abstract class AbstractFragment extends Fragment implements IActivity {
    protected IUiController mUiController;//基本的ui控制器
    protected View rootView;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUiController=getDefaultUiController(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), null);
           mUnbinder= ButterKnife.bind(this, rootView);
            initWidget();
        }
        initInstanceState(savedInstanceState);
        return rootView;
    }

    //界面布局的初始化操作
    protected void initInstanceState(Bundle savedInstanceState) {

    }

    protected IUiController getDefaultUiController(Context context) {
       return new DefaultUiController(context);
    }

    @Override
    public void showProgress() {
        if (mUiController != null) {
            mUiController.showProgress();
        }
    }

    @Override
    public void showProgress(@Nullable CharSequence msg) {
        if (mUiController != null) {
            mUiController.showProgress(msg);
        }
    }

    @Override
    public void updateProgress(int progress, int max) {
        if (mUiController != null) {
            mUiController.updateProgress(progress, max);
        }
    }

    @Override
    public void hideProgress() {
        if (mUiController != null) {
            mUiController.hideProgress();
        }
    }

    @Override
    public void showMessage(@NonNull CharSequence msg) {
        if (mUiController != null) {
            mUiController.showMessage(msg);
        }
    }

    @Override
    public void showResMessage(@StringRes int resId) {
        if (mUiController != null) {
            mUiController.showResMessage(resId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder!=null) {
            mUnbinder.unbind();
        }
    }
}
