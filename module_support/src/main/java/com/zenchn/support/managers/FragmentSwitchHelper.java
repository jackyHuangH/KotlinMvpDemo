package com.zenchn.support.managers;


import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 管理fragment切换的helper
 *
 * @author hzj
 */
public class FragmentSwitchHelper {
    // 管理类FragmentManager
    private FragmentManager mFragmentManager;
    // 容器布局id containerViewId
    private int mContainerViewId;

    /**
     * 构造函数
     *
     * @param fragmentManager 管理类FragmentManager,一般传入SupportFragmentManager
     * @param containerViewId 容器布局id containerViewId
     */
    public FragmentSwitchHelper(@Nullable FragmentManager fragmentManager, @IdRes int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;
    }

    /**
     * 清空之前的fragments
     */
    public void clearPreviousFragments() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            for (Fragment fragment : fragments) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commitNowAllowingStateLoss();
        }
    }

    /**
     * 添加Fragment
     * 此方法必须先调用
     */
    public void add(Fragment fragment) {
        if (fragment == null) {
            throw new IllegalStateException("you must call add(Fragment fragment) first!");
        }

        // 开启事物
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.remove(fragment);
            // 第一个参数是Fragment的容器id，需要添加的Fragment
            fragmentTransaction.add(mContainerViewId, fragment);
        }
        // 一定要commit
        fragmentTransaction.commit();
        //慎用，会丢失状态，不在乎状态可以用
//        fragmentTransaction.commitNowAllowingStateLoss();
    }

    /**
     * 切换显示Fragment
     */
    public void switchFragment(Fragment fragment) {
        // 开启事物
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // 1.先隐藏当前所有的Fragment
        List<Fragment> childFragments = mFragmentManager.getFragments();
        for (Fragment childFragment : childFragments) {
            if (childFragment.isAdded()) {
                fragmentTransaction.hide(childFragment);
            }
        }

        // 2.如果容器里面没有我们就添加，否则显示
        if (fragment.isAdded() || childFragments.contains(fragment)) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(mContainerViewId, fragment);
        }

        // 一定要commit
        fragmentTransaction.commit();
        //不推荐，会丢失状态
//        fragmentTransaction.commitNowAllowingStateLoss();
    }
}
