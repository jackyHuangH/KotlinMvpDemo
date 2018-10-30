package com.zenchn.support.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作    者：hzj on 2018/5/5 0005 20:55
 * 描    述：跳转路由
 * 修订记录：
 */
public class Router {

    private Intent intent;
    private Activity from;
    private Class<?> to;
    private Bundle data;
    private int requestCode = -1;

    private int enterAnim = -1;
    private int exitAnim = -1;

    private RouterCallback mCallback;

    private Router() {
        intent = new Intent();

    }

    public static Router newInstance() {
        return new Router();
    }

    public Router from(@NonNull Activity from) {
        this.from = from;
        return this;
    }

    public Router to(@NonNull Class<?> to) {
        this.to = to;
        return this;
    }

    public Router data(@Nullable Bundle data) {
        this.data = data;
        return this;
    }

    public Router putStringArray(@Nullable String key, @Nullable String[] values) {
        getBundleData().putStringArray(key, values);
        return this;
    }

    public Router putByte(@Nullable String key, byte value) {
        getBundleData().putByte(key, value);
        return this;
    }

    public Router putChar(@Nullable String key, char value) {
        getBundleData().putChar(key, value);
        return this;
    }

    public Router putString(@Nullable String key, @Nullable String value) {
        getBundleData().putString(key, value);
        return this;
    }

    public Router putShort(@Nullable String key, short value) {
        getBundleData().putShort(key, value);
        return this;
    }

    public Router putInt(@Nullable String key, int value) {
        getBundleData().putInt(key, value);
        return this;
    }

    public Router putLong(@Nullable String key, long value) {
        getBundleData().putLong(key, value);
        return this;
    }

    public Router putFloat(@Nullable String key, float value) {
        getBundleData().putFloat(key, value);
        return this;
    }

    public Router putDouble(@Nullable String key, double value) {
        getBundleData().putDouble(key, value);
        return this;
    }

    public Router putBoolean(@Nullable String key, boolean value) {
        getBundleData().putBoolean(key, value);
        return this;
    }

    public Router putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        getBundleData().putCharSequence(key, value);
        return this;
    }

    public Router putParcelable(@Nullable String key, @Nullable Parcelable value) {
        getBundleData().putParcelable(key, value);
        return this;
    }

    public Router putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        getBundleData().putParcelableArray(key, value);
        return this;
    }

    public Router putParcelableArrayList(@Nullable String key,
                                         @Nullable ArrayList<? extends Parcelable> value) {
        getBundleData().putParcelableArrayList(key, value);
        return this;
    }


    public Router putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        getBundleData().putIntegerArrayList(key, value);
        return this;
    }

    public Router putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        getBundleData().putStringArrayList(key, value);
        return this;
    }

    public Router putCharSequenceArrayList(@Nullable String key,
                                           @Nullable ArrayList<CharSequence> value) {
        getBundleData().putCharSequenceArrayList(key, value);
        return this;
    }

    public Router putSerializable(@Nullable String key, @Nullable Serializable value) {
        getBundleData().putSerializable(key, value);
        return this;
    }

    public Router requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Router anim(@AnimatorRes int enterAnim, @AnimatorRes int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    public Router anim(@AnimatorRes int... anim) {
        if (anim.length <= 2) {
            this.enterAnim = anim[0];
            this.exitAnim = anim[1];
        }
        return this;
    }

    public void launch() {
        try {
            if (intent != null && from != null && to != null) {

                if (mCallback != null) {
                    mCallback.onBefore(from, to);
                }

                intent.setClass(from, to);

                intent.putExtras(getBundleData());

                if (requestCode < 0) {
                    from.startActivity(intent);
                } else {
                    from.startActivityForResult(intent, requestCode);
                }

                if (enterAnim > 0 && exitAnim > 0) {
                    from.overridePendingTransition(enterAnim, exitAnim);
                }

                if (mCallback != null) {
                    mCallback.OnNext(from, to);
                }
            }
        } catch (Throwable throwable) {
            if (mCallback != null) {
                mCallback.onError(from, to, throwable);
            }
        }
    }

    private Bundle getBundleData() {
        if (data == null) {
            data = new Bundle();
        }
        return data;
    }

    public Router pop(@Nullable Activity activity) {
        if (activity != null)
            activity.finish();
        return this;
    }

    public Router setCallback(@Nullable RouterCallback callback) {
        if (callback != null)
            this.mCallback = callback;
        return this;
    }
}
