package com.zenchn.support.kit;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

/**
 * 作    者：wangr on 2017/5/23 13:49
 * 描    述：android系统常用方法的封装
 * 修订记录：
 */
public class AndroidKit {

    private static final String TAG = "AndroidKit";

    /**
     * 作    者：wangr on 2017/5/24 17:15
     * 描    述：包信息的封装
     * 修订记录：
     */
    public static class Package {

        /**
         * 获取指定包名的app信息
         *
         * @param context
         * @return
         */
        public static PackageInfo getPackageInfo(@NonNull Context context, @NonNull String packageName) {
            final PackageManager mPackageManager = context.getPackageManager();// 获取packageManager
            PackageInfo mPackageInfo = null;
            try {
                mPackageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage());
            }
            return mPackageInfo;
        }

        /**
         * 获取指定包名的app信息
         *
         * @param context
         * @param packageName
         * @return
         */
        public static ApplicationInfo getApplicationInfo(@NonNull Context context, @NonNull String packageName) {
            PackageInfo mPackageInfo = getPackageInfo(context, packageName);
            return mPackageInfo != null ? mPackageInfo.applicationInfo : null;
        }

        /**
         * 获取指定包名的Meta-Data
         *
         * @param context
         * @param packageName
         * @param key
         * @return
         */
        public static String getAppMetaData(@NonNull Context context, @NonNull String packageName, @NonNull String key) {
            if (TextUtils.isEmpty(key)) {
                return null;
            }

            String value = null;
            try {
                PackageManager mPackageManager = context.getPackageManager();
                if (mPackageManager != null) {
                    ApplicationInfo applicationInfo = mPackageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            value = applicationInfo.metaData.getString(key);
                        }
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }

            return value;
        }

        /**
         * 获取当前app信息
         *
         * @param context
         * @return
         */
        public static PackageInfo getPackageInfo(@NonNull Context context) {
            return getPackageInfo(context, context.getPackageName());
        }

        /**
         * 获取当前app版本号
         *
         * @param context
         * @return
         */
        public static int getVersionCode(@NonNull Context context) {
            PackageInfo mPackageInfo = getPackageInfo(context, context.getPackageName());
            return mPackageInfo != null ? mPackageInfo.versionCode : 0;
        }

        /**
         * 获取当前app版本名
         *
         * @param context
         * @return
         */
        public static String getVersionName(@NonNull Context context) {
            PackageInfo mPackageInfo = getPackageInfo(context, context.getPackageName());
            return mPackageInfo != null ? mPackageInfo.versionName : "";
        }

        /**
         * 安装一个apk文件
         *
         * @param context
         * @param uriFile
         */
        public static void install(@NonNull Context context, @NonNull java.io.File uriFile) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        /**
         * 卸载一个app
         *
         * @param context
         * @param packageName
         */
        public static void uninstall(@NonNull Context context, @NonNull String packageName) {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);// 创建Intent意图
            context.startActivity(intent);
        }

        /**
         * 根据包名判断手机上是否安装了指定的软件
         *
         * @param context
         * @param packageName
         * @return
         */
        public static boolean isPackageExists(@NonNull Context context, @NonNull String packageName) {
            final PackageManager mPackageManager = context.getPackageManager();// 获取packageManager
            if (mPackageManager == null || TextUtils.isEmpty(packageName)) {
                return false;
            }

            List<PackageInfo> mPackageInfo = mPackageManager.getInstalledPackages(0); // 获取所有已安装程序的包信息
            if (mPackageInfo != null) {
                for (PackageInfo packageInfo : mPackageInfo) {
                    if (packageInfo != null && packageInfo.packageName.equals(packageName))
                        return true;

                }
            }
            return false;
        }

        /**
         * 判断是否是系统App
         *
         * @param context
         * @param packageName 包名
         * @return
         */
        public static boolean isSystemApp(@NonNull Context context, @NonNull String packageName) {
            try {
                ApplicationInfo mApplicationInfo = getApplicationInfo(context, packageName);
                return (mApplicationInfo != null && (mApplicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage());
            }
            return false;
        }

        /**
         * 判断某个包名是否运行在顶层
         *
         * @param context
         * @param packageName
         * @return
         */
        public static Boolean isTopActivity(@NonNull Context context, @NonNull String packageName) {
            if (TextUtils.isEmpty(packageName)) {
                return null;
            }

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo == null || tasksInfo.isEmpty()) {
                return null;
            }
            try {
                return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage());
                return false;
            }
        }


        /**
         * 判断当前应用是否运行在后台
         *
         * @param context
         * @return
         */
        public static boolean isApplicationInBackground(@NonNull Context context) {
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskList = mActivityManager.getRunningTasks(1);
            if (taskList != null && !taskList.isEmpty()) {
                ComponentName topActivity = taskList.get(0).topActivity;
                if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * 作    者：wangr on 2017/5/25 13:31
     * 描    述：像素密度换算的封装
     * 修订记录：
     */
    public static class Dimens {

        public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
        public static final float SCALED_DENSITY = Resources.getSystem().getDisplayMetrics().scaledDensity;


        /**
         * 获取DisplayMetrics对象
         *
         * @return
         */
        public static DisplayMetrics getDisPlayMetrics() {
            return Resources.getSystem().getDisplayMetrics();
        }

        /**
         * 获取屏幕的宽度（像素）
         *
         * @return
         */
        public static int getScreenWidth() {
            return getDisPlayMetrics().widthPixels;
        }

        /**
         * 获取屏幕的高（像素）
         *
         * @return
         */
        public static int getScreenHeight() {
            return getDisPlayMetrics().heightPixels;
        }

        /**
         * dp转px，保证尺寸大小不变
         *
         * @param dpValue
         * @return
         */
        public static int dp2px(int dpValue) {
            return Math.round(dpValue * DENSITY);
        }

        /**
         * px转dp，保证尺寸大小不变
         *
         * @param pxValue
         * @return
         */
        public static int px2dp(int pxValue) {
            return Math.round(pxValue / DENSITY);
        }

        /**
         * px转sp，保证尺寸大小不变
         *
         * @param pxValue
         * @return
         */
        public static int px2sp(float pxValue) {
            return Math.round(pxValue / SCALED_DENSITY);
        }

        /**
         * sp转px，保证尺寸大小不变
         *
         * @param spValue
         * @return
         */
        public static int sp2px(float spValue) {
            return Math.round(spValue * SCALED_DENSITY);
        }

        /**
         * 获取状态栏高度——方法1
         */
        public static int getStatusBarHeight() {
            int statusBarHeight = 0;
            //获取status_bar_height资源的ID
            int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight = Resources.getSystem().getDimensionPixelSize(resourceId);
            }
            return statusBarHeight;
        }
    }

    /**
     * 作    者：wangr on 2017/5/25 13:29
     * 描    述： 网络相关的封装
     * 修订记录：
     */
    public static class NetWork {

        public static final int NETWORK_TYPE_NONE = -1;//无网络

        public static final int NETWORK_TYPE_INVALID = 0;//无网络
        public static final int NETWORK_TYPE_WAP = 1;//wap网络
        public static final int NETWORK_TYPE_2G = 2;//2G网络
        public static final int NETWORK_TYPE_3G = 3;//G和3G以上网络，或统称为快速网络
        public static final int NETWORK_TYPE_WIFI = 4;//wifi网络


        /**
         * 判断当前网络是否可用(是否可用)
         *
         * @param context
         * @return 获取网络信息实体
         * 由于从系统服务中获取数据属于进程间通信，基本类型外的数据必须实现Parcelable接口，
         * NetworkInfo实现了Parcelable，获取到的activeNetInfo相当于服务中网络信息实体对象的一个副本（拷贝），
         * 所以，不管系统网络服务中的实体对象是否置为了null，此处获得的activeNetInfo均不会发生变化
         */
        public static boolean isNetworkAvailable(@NonNull Context context) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();//获取当前连接可用的网络
                if (mActiveNetworkInfo != null) {
                    return mActiveNetworkInfo.isConnected() && mActiveNetworkInfo.isAvailable();
                }
            }
            return false;
        }

        /**
         * 判断当前可用的网络是否是wifi网络
         *
         * @param context
         * @return
         */
        public static boolean isWifiAvailable(@NonNull Context context) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();//获取当前连接可用的网络
                if (mActiveNetworkInfo != null) {
                    return mActiveNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI && mActiveNetworkInfo.isAvailable();
                }
            }
            return false;
        }

        /**
         * 判断当前可用的网络是否是移动网络
         *
         * @param context
         * @return
         */
        public static boolean isMoNetAvailable(@NonNull Context context) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();//获取当前连接可用的网络
                if (mActiveNetworkInfo != null) {
                    return mActiveNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE && mActiveNetworkInfo.isAvailable();
                }
            }
            return false;
        }

        /**
         * 判断wifi网络是否连接并可用
         */
        public static boolean isWifiEnabled(@NonNull Context context) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isConnected();
                }
            }
            return false;
        }

        /**
         * 判断移动网络是否连接并可用
         *
         * @param context
         * @return
         */
        public static boolean isMoNetEnabled(@NonNull Context context) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isConnected();
                }
            }
            return false;
        }

        /**
         * 判断gps是否打开并可用
         *
         * @param context
         * @return
         */
        public static boolean isGpsEnabled(@NonNull Context context) {
            LocationManager mLocationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
            List<String> mAccessibleProviders = mLocationManager.getProviders(true);
            return mAccessibleProviders != null && mAccessibleProviders.size() > 0;
        }

        /**
         * 获取当前网络类型
         *
         * @param context
         * @return
         */
        public static int getNetworkType(Context context) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mActiveNetworkInfo != null) {
                    return mActiveNetworkInfo.getType();
                }
            }
            return NETWORK_TYPE_NONE;
        }

        /**
         * 获取当前网络状态（wifi,wap,2g,3g）
         *
         * @param context
         * @return {@link #NETWORK_TYPE_2G},
         * {@link #NETWORK_TYPE_3G},
         * {@link #NETWORK_TYPE_INVALID},
         * {@link #NETWORK_TYPE_WAP}，
         * {@link #NETWORK_TYPE_WIFI}
         */
        public static int getNetWorkType(@NonNull Context context) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                    int type = mNetworkInfo.getType();
                    switch (type) {
                        case ConnectivityManager.TYPE_WIFI:
                            return NETWORK_TYPE_WIFI;
                        case ConnectivityManager.TYPE_MOBILE:
                            String proxyHost = android.net.Proxy.getDefaultHost();
                            return TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G) : NETWORK_TYPE_WAP;
                    }
                }
            }
            return NETWORK_TYPE_INVALID;
        }

        /**
         * 判断是否处于高速网络
         *
         * @param context
         * @return
         */
        private static boolean isFastMobileNetwork(Context context) {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyManager != null) {
                switch (mTelephonyManager.getNetworkType()) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        return false;
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        return false;
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return false;
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        return false;
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return false;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return true;
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        return false;
                    default:
                        return false;
                }
            }
            return false;
        }
    }

    /**
     * 作    者：wangr on 2017/5/25 17:17
     * 描    述：软键盘封装
     * 修订记录：
     */
    public static class Keyboard {

        /**
         * 获取软键盘显示状态
         *
         * @param activity
         * @return
         */
        public static boolean isSoftInputShow(@NonNull Activity activity) {
            return activity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
        }

        /**
         * 动态显示软键盘
         *
         * @param context
         * @param edit
         */
        public static void showSoftInput(@NonNull Context context, EditText edit) {
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            edit.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edit, 0);
        }

        /**
         * 动态显示软键盘
         *
         * @param context
         */
        public static void showSoftInput(@NonNull Context context) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

        /**
         * 动态隐藏软键盘
         *
         * @param context
         */
        public static void hideSoftInput(@NonNull Context context) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
            if (imm.isActive()) {//如果开启
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
            }
        }

        /**
         * 动态隐藏软键盘
         *
         * @param activity
         */
        public static void hideSoftInput(@NonNull Activity activity) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                View focusView = activity.getCurrentFocus();
                if (focusView != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }

        /**
         * 切换键盘显示与否状态
         *
         * @param context
         */
        public static void toggleSoftInput(@NonNull Context context) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

    }

    /**
     * 作    者：wangr on 2017/5/25 17:17
     * 描    述：相机封装
     * 修订记录：
     */
    public static class Camera {

        public static android.hardware.Camera camera;

        /**
         * 检测是否存在相机
         *
         * @param context
         * @return
         */
        public static boolean hasCameraDevice(@NonNull Context context) {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        }

        /**
         * 检测相机是否支持自动对焦
         *
         * @param camera
         * @return
         */
        public static boolean isAutoFocusSupported(android.hardware.Camera camera) {
            if (camera != null) {
                android.hardware.Camera.Parameters mParameters = camera.getParameters();
                List<String> modes = mParameters.getSupportedFocusModes();
                return modes.contains(android.hardware.Camera.Parameters.FOCUS_MODE_AUTO);
            }
            return false;
        }

        /**
         * 设置相机预览方向
         *
         * @param context
         */
        public static void followScreenOrientation(@NonNull Context context) {
            final int orientation = context.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                camera.setDisplayOrientation(180);
            } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                camera.setDisplayOrientation(90);
            }
        }

        /**
         * 开启相机
         */
        public static android.hardware.Camera prepareCamera() {
            if (camera == null)
                camera = android.hardware.Camera.open();
            return camera;
        }

        /**
         * 关闭相机，释放资源
         */
        public static void releaseCamera() {
            if (camera != null) {
                camera.release();
                camera = null;
            }
        }

        /**
         * 请求自动对焦
         *
         * @param callback
         */
        public static void requestAutoFocus(android.hardware.Camera.AutoFocusCallback callback) {
            if (camera != null)
                camera.autoFocus(callback);
        }

        /**
         * 打开闪光灯
         */
        public static void lightOn() {
            if (camera != null) {
                android.hardware.Camera.Parameters parameter = camera.getParameters();
                parameter.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameter);
            }
        }

        /**
         * 关闭闪光灯
         */
        public static void lightOff() {
            if (camera != null) {
                android.hardware.Camera.Parameters parameter = camera.getParameters();
                parameter.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameter);
            }
        }
    }

}
