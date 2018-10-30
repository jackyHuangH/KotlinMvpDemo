package com.zenchn.support;

import android.os.Environment;

/**
 * 作    者：wangr on 2017/8/26 15:25
 * 描    述：
 * 修订记录：
 */
public class GlobalConfig {

    public static final String DEFAULT_TAG = "huangzj";

    // #crash 是否收集报错日志
    public static final boolean isReport = true;
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/zenchn/library/log/";
    public static final String FILE_NAME_PREFIX = "crash";
    public static final String FILE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FILE_NAME_SUFFIX = ".log";
}
