package com.zenchn.apilib.util;

import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zenchn.apilib.BuildConfig;

/**
 * 作    者：hzj on 2018/9/12 10:00
 * 描    述：Logger 2.2.0版本封装
 * 修订记录：
 */
public class LoggerKit {

    /**
     * application中调用此初始化方法
     *
     * @param tag
     */
    public static void init(String tag) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(tag)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }


    public static void log(int priority, String tag, String message, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Logger.log(priority, tag, message, throwable);
        }
    }

    public static void d(String message, @Nullable Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.d(message, args);
        }
    }

    public static void d(Object object) {
        if (BuildConfig.DEBUG) {
            Logger.d(object);
        }
    }

    public static void e(Throwable throwable, String message, @Nullable Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.e(throwable, message, args);
        }
    }

    public static void e(String message, @Nullable Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.e(message, args);
        }
    }

    public static void e(Exception e, @Nullable Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.e(e.toString(), args);
        }
    }

    public static void i(String message, @Nullable Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.i(message, args);
        }
    }

    public static void v(String message, @Nullable Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.v(message, args);
        }
    }

    public static void w(String message, @Nullable Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.w(message, args);
        }
    }

    public static void wtf(String message, @Nullable Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.wtf(message, args);
        }
    }

    public static void json(String json) {
        if (BuildConfig.DEBUG) {
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if (BuildConfig.DEBUG) {
            Logger.xml(xml);
        }
    }

}
