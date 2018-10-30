package com.zenchn.support.widget.tips;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zenchn.support.R;


/**
 * 作    者：wangr on 2017/4/24 11:05
 * 描    述：Toast工具类
 * 修订记录：
 */
public class SuperToast {

    private SuperToast() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static final Object mSynObj = new Object();
    private static Toast mToast;
    private static Toast mDefaultToast;
    private static View mCustomToastView;
    private static TextView mToastTextView;

    /**
     * 初始化toast样式
     *
     * @param customToastView
     */
    public static void setCustomToastView(View customToastView) {
        SuperToast.mCustomToastView = customToastView;
        SuperToast.mToastTextView = (TextView) customToastView.findViewById(R.id.mMessage);
        if (mToastTextView == null) {
            throw new IllegalStateException("make toast without text view ...");
        }
    }

    /**
     * 系统默认的Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param context
     * @param msg
     */
    public static void showDefaultMessage(Context context, String msg) {
        showDefaultToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    /**
     * 系统默认的Toast发送消息
     *
     * @param context
     * @param msg
     */
    public static void showDefaultMessageLong(Context context, String msg) {
        showDefaultToast(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
    }

    /**
     * 系统默认的Toast发送消息
     *
     * @param context
     * @param msg
     * @param len
     */
    private static void showDefaultToast(final Context context, final String msg, final int len) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                synchronized (mSynObj) {
                    if (mDefaultToast != null) {
                        mDefaultToast.cancel();
                    }
                    mDefaultToast = Toast.makeText(context, msg, len);
                    mDefaultToast.show();
                }
            }
        });
    }

    /**
     * 发送自定义样式的toast
     *
     * @param context
     * @param msg
     */
    public static void showCustomMessage(Context context, String msg) {
        if (mCustomToastView != null) {
            showCustomToast(context.getApplicationContext(), msg, mCustomToastView);
        } else {
            showDefaultMessage(context.getApplicationContext(), msg);
        }
    }

    /**
     * Toast发送自定义样式
     *
     * @param context
     * @param msg
     * @param view
     */
    private static void showCustomToast(final Context context, final String msg, final View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (mSynObj) {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = new Toast(context);
                    if (mToastTextView == null) {
                        mToastTextView = (TextView) view.findViewById(R.id.mMessage);
                    }
                    mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    mToast.setView(view);
                    mToast.setDuration(Toast.LENGTH_LONG);
                    mToastTextView.setText(msg);
                    mToast.show();
                }
            }
        });
    }

    /**
     * 关闭当前Toast
     */
    public static void cancelCurrentToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        if (mDefaultToast != null) {
            mDefaultToast.cancel();
            mDefaultToast = null;
        }
    }
}
