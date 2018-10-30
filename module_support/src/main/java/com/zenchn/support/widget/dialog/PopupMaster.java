package com.zenchn.support.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.HashMap;
import java.util.Map;


/**
 * Popupwindow 弹窗工具类
 */
public class PopupMaster {
    public PopupMaster() {
    }

    private NougatPopWindow popupWindow;

    public int x = 0;
    public int y = 0;

    public int gravity;

    public static class Builder {
        public Builder() {
        }

        public interface WindowLayoutInit {
            void OnWindowLayoutInit(View rootView);
        }

        private WindowLayoutInit layoutInit;

        private Context context;
        private int layout;
        private boolean focusable = false;
        private boolean outSideTouchable = true;

        private int width = ViewGroup.LayoutParams.MATCH_PARENT;
        private int height = ViewGroup.LayoutParams.MATCH_PARENT;

        private int x = 0;
        private int y = 0;

        private int animateId = 0;

        private int gravity;

        private Handler handler;

        private PopupWindow.OnDismissListener dl;

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

        public Builder setWidth(int w) {
            this.width = w;
            return this;
        }

        public Builder setHeight(int h) {
            this.height = h;
            return this;
        }

        public Builder setPositionX(int x) {
            this.x = x;
            return this;
        }

        public Builder setPositionY(int y) {
            this.y = y;
            return this;
        }

        public Builder setAnimateId(int id) {
            this.animateId = id;
            return this;
        }

        public Builder setOutsideTouchable(boolean f) {
            this.outSideTouchable = f;
            return this;
        }

        public Builder setFocusable(boolean f) {
            this.focusable = f;
            return this;
        }

        public Builder setTouchListener(View.OnTouchListener listener) {
            this.l = listener;
            return this;
        }

        public Builder setDismissListener(PopupWindow.OnDismissListener dl) {
            this.dl = dl;
            return this;
        }

        public Builder setHandler(Handler handler) {
            this.handler = handler;
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

        public PopupMaster create() {
            final NougatPopWindow popupWindow = new NougatPopWindow();
            final View content = LayoutInflater.from(this.context).inflate(this.layout, null);

            if (this.layoutInit != null) {
                layoutInit.OnWindowLayoutInit(content);
            }

            for (Integer id : listeners.keySet()) {
                View item = content.findViewById(id);
                item.setOnClickListener(this.listeners.get(id));
            }

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    popupWindow.dismiss();
                }
            });

            popupWindow.setContentView(content);
            popupWindow.setTouchable(true);
            popupWindow.setWidth(this.width);
            popupWindow.setHeight(this.height);
            popupWindow.setFocusable(this.focusable);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(this.outSideTouchable);

            if (animateId > 0)
                popupWindow.setAnimationStyle(animateId);

            if (dl != null)
                popupWindow.setOnDismissListener(dl);

            if (this.l != null)
                popupWindow.setTouchInterceptor(l);
            else
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
//                        popupWindow.dismiss();
                        return false;
                    }
                });

            PopupMaster mask = new PopupMaster();
            mask.popupWindow = popupWindow;
            mask.x = this.x;
            mask.y = this.y;
            mask.gravity = this.gravity;

            return mask;
        }
    }

    /**
     * 设置添加屏幕的背景透明度,一定要在OnDismissListener中将透明度设置回来
     *
     * @param bgAlpha 0.0f-1.0f
     */
    public void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public void show(View view) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAtLocation(view, gravity, x, y);
            popupWindow.update();
        }
    }

    public void showAtBottom(View view) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            popupWindow.showAtLocation(view, gravity, location[0], location[1] + view.getHeight() + 60);
            popupWindow.update();//一定要调用,否则无效
        }
    }

    public void showAsDropDown(View view, int xoff, int yoff, int gravity) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAsDropDown(view, xoff, yoff, gravity);
        }
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.setOnDismissListener(listener);
        }
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public void close() {
        if (popupWindow != null)
            popupWindow.dismiss();
    }
}
