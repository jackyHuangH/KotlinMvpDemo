package com.zenchn.apilib.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.zenchn.apilib.entity.LoginInfoEntity;

import java.util.Locale;

/**
 * 描    述：设备信息工具类
 * 修订记录：
 *
 * @author HZJ
 */
public class DeviceUtils {

    /**
     * 用户登陆令牌授权时获取用户信息(获取令牌)
     *
     * @param context
     * @return
     */
    public static LoginInfoEntity getClientInfo(Context context) {
        LoginInfoEntity loginInfo = new LoginInfoEntity();
        // 返回手机唯一标示
        loginInfo.setDeviceId(getAndroidId(context));
        //返回手机型号-系统版本
        loginInfo.setDeviceName(String.format(Locale.CHINA, "%1$s-%2$s", Build.MODEL, getSDKVersionName()));
        return loginInfo;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getMobileModel() {
        String model = Build.MODEL;
        model = model.trim();
        return model;
    }

    @SuppressLint("HardwareIds")
    private static String getAndroidId(Context context) {
        try {
            context = context.getApplicationContext();
            ContentResolver contentResolver = context.getContentResolver();
            return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Return the version name of device's system.
     *
     * @return the version name of device's system
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Return version code of device's system.
     *
     * @return version code of device's system
     */
    public static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

//    private static String getUniqueId(Context context) {
//        try {
//            context = context.getApplicationContext();
//            ContentResolver contentResolver = context.getContentResolver();
//            TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//                String IMEI = "";
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    IMEI = telephonyMgr.getImei();
//                } else {
//                    IMEI = telephonyMgr.getDeviceId();
//                }
//                String simSerialNumber = telephonyMgr.getSimSerialNumber();
//                String androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
//                UUID deviceUuid = new UUID(androidId.hashCode(), ((long) IMEI.hashCode() << 32) | simSerialNumber.hashCode());
//                return deviceUuid.toString();
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
