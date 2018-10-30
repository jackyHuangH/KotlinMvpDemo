package com.zenchn.support.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static boolean getIsExpires(String expires) {
        boolean isexpires = true;
        if (expires.trim().length() > 0) {
            if (((new Date(Long.parseLong(expires + "000"))).getTime() - 600) < (new Date()).getTime()) {
                isexpires = true;
            } else {
                isexpires = false;
            }
        } else {
            isexpires = true;
        }
        return isexpires;
    }

    public static boolean isExpires(String expires) {
        boolean isexpires = true;
        if (expires.trim().length() > 0) {
            if ((new Date(Long.parseLong(expires + "000"))).getTime() < ((new Date()).getTime())) {
                isexpires = true;
            } else {
                isexpires = false;
            }
        } else {
            isexpires = true;
        }
        return isexpires;
    }

    /**
     * 格式化录制视频的时间 mm:ss
     * @param duration
     * @return
     */
    public static String getMinSecTime(Long duration) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        Date date = new Date(duration);
        return format.format(date);
    }


    public static String getTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(Long.parseLong(time + "000"));
        return format.format(date);
    }

    public static String getDateByFormat(long time, String _format) {
        SimpleDateFormat format = new SimpleDateFormat(_format);
        Date date = new Date(time * 1000);
        return format.format(date);
    }

    public static String getRegistrationTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date(Long.parseLong(time + "000"));
        return format.format(date);
    }

    public static String getTimeDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        Date date = new Date(Long.parseLong(time + "000"));
        return format.format(date);
    }

    /**
     * 返回 yyyy-MM-dd 格式日期
     * @param time
     * @return
     */
    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(Long.parseLong(time + "000"));
        return format.format(date);
    }

    public static String getDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(Long.parseLong(time + "000"));
        return format.format(date);
    }

    public static String toDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date());
    }

    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date());
    }

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param previosTime
     * @return
     */
    public static String getFormerlyTime(String previosTime) {
        Long oldTime = Long.valueOf(previosTime);
        if (oldTime == 0) {
            return "";
        }
        long now = System.currentTimeMillis();
        long diff = now - oldTime * 1000;
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }


    /**
     * 获取小时
     * @return
     */
    public static int getNowHour() {
        int hour = 0;
        try {
            SimpleDateFormat formatHour = new SimpleDateFormat("HH");
            hour = Integer.valueOf(formatHour.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hour;
    }



    /**
     * 把标准格式yyyy-MM-dd HH:mm日期转成时间戳,单位秒
     * @param time
     * @return long
     */
    public static long getTimeToTimeStamp(String time) {
        long timeStamp = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = format.parse(time);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp / 1000;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 将倒计时的时间格式化成分:秒"mm:ss"的格式
     * @param millisInfuture 剩余倒计时时长,单位:毫秒
     * @return
     */
    public static String formatCountTimer(long millisInfuture){
        String secondStr = "";
        String minStr = "";
        long remainTime = millisInfuture / 1000;
        long second = remainTime % 60;
        long min = remainTime / 60;
        if (second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = second + "";
        }

        if (min < 10) {
            minStr = "0" + min;
        } else {
            minStr = min + "";
        }
        return minStr+":"+secondStr;
    }

}
