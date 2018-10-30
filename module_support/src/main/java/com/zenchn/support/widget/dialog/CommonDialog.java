package com.zenchn.support.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zenchn.support.R;


/**
 * 作    者：wangr on 2017/9/4 17:46
 * 描    述：
 * 修订记录：
 */

public class CommonDialog extends Dialog {

    public CommonDialog(Context context) {
        super(context);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {

        private Context mContext;
        private String mMessage;
        private String mPositiveButtonText;
        private String mNegativeButtonText;

        private OnClickListener mPositiveButtonClickListener;
        private OnClickListener mNegativeButtonClickListener;
        private boolean mCancelable;
        private boolean mCanceledOnTouchOutside;
        private boolean isHidePositiveButton;
        private boolean isHideNegativeButton;
        private float mButtonTextSize;
        private Drawable mDrawable;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder message(@NonNull String message) {
            this.mMessage = message;
            return this;
        }

        public Builder message(@StringRes int resId) {
            this.mMessage = mContext.getResources().getString(resId);
            return this;
        }

        public Builder messageDrawable(Drawable mDrawable) {
            this.mDrawable = mDrawable;
            return this;
        }

        public Builder messageDrawable(@DrawableRes int resId) {
            this.mDrawable = mContext.getResources().getDrawable(resId);
            return this;
        }

        public Builder buttonTextSize(float buttonTextSize) {
            this.mButtonTextSize = buttonTextSize;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder hidePositiveButton(boolean hidePositiveButton) {
            this.isHidePositiveButton = hidePositiveButton;
            return this;
        }

        public Builder hideNegativeButton(boolean hideNegativeButton) {
            this.isHideNegativeButton = hideNegativeButton;
            return this;
        }

        public Builder positiveButton(@StringRes int resId, OnClickListener listener) {
            this.mPositiveButtonText = mContext.getResources().getString(resId);
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder positiveButton(String positiveButtonText, OnClickListener listener) {

            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public void positiveButtonText(String positiveButtonText) {
            this.mPositiveButtonText = positiveButtonText;
        }


        public Builder negativeButton(@StringRes int resId, OnClickListener listener) {
            this.mNegativeButtonText = mContext.getResources().getString(resId);
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        public Builder negativeButton(String negativeButtonText, OnClickListener listener) {

            this.mNegativeButtonClickListener = listener;
            return this;
        }

        public void negativeButtonText(String negativeButtonText) {
            this.mNegativeButtonText = negativeButtonText;
        }

        public CommonDialog build() {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final CommonDialog dialog = new CommonDialog(mContext, R.style.CustomDialog);

            View rootView = inflater.inflate(R.layout.dialog_common_layout, null);

            //是否隐藏积极按钮
            if (isHidePositiveButton) {
                rootView.findViewById(R.id.mPositiveButton).setVisibility(View.GONE);
            } else {
                if (mPositiveButtonText != null) {
                    Button mPositiveButton = (Button) rootView.findViewById(R.id.mPositiveButton);
                    mPositiveButton.setText(mPositiveButtonText);
                    if (mButtonTextSize != 0) {
                        mPositiveButton.setTextSize(mButtonTextSize);
                    }
                    if (mPositiveButtonClickListener != null) {
                        mPositiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPositiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            }
                        });
                    }
                }
            }

            //是否隐藏消极按钮
            if (isHideNegativeButton) {
                rootView.findViewById(R.id.mNegativeButton).setVisibility(View.GONE);
            } else {
                if (mNegativeButtonText != null) {
                    Button mPositiveButton = (Button) rootView.findViewById(R.id.mNegativeButton);
                    mPositiveButton.setText(mNegativeButtonText);
                    if (mButtonTextSize != 0) {
                        mPositiveButton.setTextSize(mButtonTextSize);
                    }
                    if (mNegativeButtonClickListener != null) {
                        mPositiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mNegativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
                    }
                }
            }

            if (mMessage != null) {
                TextView tvMessage = (TextView) rootView.findViewById(R.id.mMessage);
                tvMessage.setText(mMessage);
                if (mDrawable != null) {
                    tvMessage.setCompoundDrawablePadding(20);
                    tvMessage.setCompoundDrawablesWithIntrinsicBounds(null, mDrawable, null, null);
                }
            }

            dialog.addContentView(rootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            dialog.setCancelable(mCancelable);

            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);

            return dialog;
        }
    }

}

