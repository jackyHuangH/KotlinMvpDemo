package com.zenchn.support.widget.dialog;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;

import com.zenchn.support.R;

import java.util.HashMap;
import java.util.Map;



/**
 * Dialog 弹窗工具类
 * author:hzj 使用Dialog类，并使用setContentView()方法设置时，宽度两边无边距。
 * 使用AlertDialog时宽度两边有边距。
 */
public class DialogMaster {
    public DialogMaster() {
    }

    private AlertDialog mDialog;
    private View mContentView;

    //宽，默认wrap_content
    private int mDialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    //高，默认wrap_content
    private int mDialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    public int mGravity;

    public static class Builder {
        public Builder() {
        }

        public interface WindowLayoutInit {
            void OnWindowLayoutInit(View rootView);
        }

        private WindowLayoutInit layoutInit;

        private Context context;
        private int layout;
        private boolean cancelabe = true;
        private boolean canceledOnTouchOutside = true;
        //宽，默认wrap_content
        private int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        //高，默认wrap_content
        private int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        private int gravity = Gravity.CENTER;

        private DialogInterface.OnDismissListener dl;

        private Map<Integer, View.OnClickListener> listeners = new HashMap<>();

        private View.OnTouchListener l;

        public Builder setLayout(int id) {
            this.layout = id;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setLayoutInit(WindowLayoutInit layoutInit) {
            this.layoutInit = layoutInit;
            return this;
        }


        public Builder setDialogWidth(int width) {
            this.dialogWidth = width;
            return this;
        }

        public Builder setDialogHeight(int height) {
            this.dialogHeight = height;
            return this;
        }

        public Builder setCancelabe(boolean f) {
            this.cancelabe = f;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean f) {
            this.canceledOnTouchOutside = f;
            return this;
        }

        public Builder setDismissListener(DialogInterface.OnDismissListener dl) {
            this.dl = dl;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setItemClickListener(int id, View.OnClickListener listener) {
            this.listeners.put(id, listener);
            return this;
        }

        public DialogMaster create() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            AlertDialog dialog = builder.create();
            final View contentView = LayoutInflater.from(this.context).inflate(this.layout, null);

            if (this.layoutInit != null) {
                layoutInit.OnWindowLayoutInit(contentView);
            }

            for (Integer id : listeners.keySet()) {
                View item = contentView.findViewById(id);
                item.setOnClickListener(this.listeners.get(id));
            }

            dialog.setCancelable(this.cancelabe);
            dialog.setCanceledOnTouchOutside(this.canceledOnTouchOutside);

            //消除背景色，此方法设置后才能Match_parent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            if (dl != null) {
                dialog.setOnDismissListener(dl);
            }

            DialogMaster mask = new DialogMaster();
            mask.mDialog = dialog;
            mask.mDialogWidth = this.dialogWidth;
            mask.mDialogHeight = this.dialogHeight;
            mask.mGravity = this.gravity;
            mask.mContentView = contentView;

            return mask;
        }
    }

    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        }
        return false;
    }

    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
            //设置dialog的宽高为屏幕的宽高
            Window window = mDialog.getWindow();
            window.setGravity(mGravity);
            // 添加动画
            window.setWindowAnimations(R.style.Dialog_default_Anim);
            window.getDecorView().setPadding(0, 0, 0, 0);

            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = mDialogWidth;
            lp.height = mDialogHeight;
            window.setAttributes(lp);
            //setContentView()必须在show()之后
            mDialog.setContentView(mContentView);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showRippleAnimator() {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(mContentView,
                        (int) (mContentView.getWidth() * 0.5F),
                        mContentView.getHeight(),
                        0,
                        (float) Math.hypot(mContentView.getWidth(), mContentView.getHeight()));
                circularReveal.setDuration(1000);
                circularReveal.setInterpolator(new AnticipateOvershootInterpolator());
                circularReveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                circularReveal.start();
            }
        });
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void dismissRippleAnimator() {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(mContentView,
                        (int) (mContentView.getWidth() * 0.5F),
                        mContentView.getHeight(),
                        (float) Math.hypot(mContentView.getWidth(), mContentView.getHeight()),
                        0);
                circularReveal.setDuration(500);
                circularReveal.setInterpolator(new AccelerateInterpolator());
                circularReveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.dismiss();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                circularReveal.start();
            }
        });
    }
}
