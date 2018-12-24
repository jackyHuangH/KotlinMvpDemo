package com.zenchn.support.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    //基本的ui控制器
    protected IUiController mUiController;
    protected View rootView;
    private Unbinder mUnbinder;

    /**支持懒加载配置**/

    /**
     * rootView是否初始化，防止回调在rootView为空时触发
     */
    private boolean mHasViewCreated;

    /**
     * 当前fragment是否可见，防止viewPager缓存机制导致回调函数触发
     */
    private boolean mIsFragmentVisible;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUiController = getDefaultUiController(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHasViewCreated = false;
        mIsFragmentVisible = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), null);
            mUnbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidget();
        initInstanceState(savedInstanceState);
        if (!mHasViewCreated && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            mIsFragmentVisible = true;
        }
    }

    //界面布局的初始化操作
    protected void initInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("AbsFragment", "setUserVisibleHint->isVisibleToUser:" + isVisibleToUser);
        if (rootView == null) {
            return;
        }
        mHasViewCreated = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            mIsFragmentVisible = true;
            return;
        }
        if (mIsFragmentVisible) {
            onFragmentVisibleChange(false);
            mIsFragmentVisible = false;
        }
    }

    protected void onFragmentVisibleChange(boolean isFragmentVisible) {
        if (isFragmentVisible) {
            onSupportVisible();
        }
    }

    /**
     * fragment每次对用户可见时
     */
    protected abstract void onSupportVisible();

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
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
