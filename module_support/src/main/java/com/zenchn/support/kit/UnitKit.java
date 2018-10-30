package com.zenchn.support.kit;

/**
 * 作    者：wangr on 2017/5/24 16:55
 * 描    述：常用计量单位
 * 修订记录：
 */
public class UnitKit {


    /********************
     * 存储相关常量
     ********************/

    public static final int KB = 2 << 9;//KB与Byte的倍数
    public static final int MB = 2 << 19;//MB与Byte的倍数
    public static final int GB = 2 << 29;//GB与Byte的倍数

    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

    /********************
     * 时间相关常量
     ********************/

    public static final int SEC = 1000;
    public static final int MIN = 60 * SEC;
    public static final int HOUR = 60 * MIN;
    public static final int DAY = 24 * HOUR;

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }
}
