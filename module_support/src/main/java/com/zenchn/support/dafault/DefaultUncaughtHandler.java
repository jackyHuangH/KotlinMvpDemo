package com.zenchn.support.dafault;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zenchn.support.GlobalConfig;
import com.zenchn.support.base.ICrashCallback;
import com.zenchn.support.base.ICrashConfig;
import com.zenchn.support.utils.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作    者：wangr on 2017/5/4 10:54
 * 描    述：异常信息捕获者,持久化异常信息到本地
 * 修订记录：
 */
public final class DefaultUncaughtHandler implements UncaughtExceptionHandler {

    private ICrashConfig mCrashConfig;
    private ICrashCallback mCrashCallback;
    private Context mContext;

    private DefaultUncaughtHandler() {
    }

    private static class SingletonInstance {
        private static final DefaultUncaughtHandler INSTANCE = new DefaultUncaughtHandler();
    }

    public static DefaultUncaughtHandler getInstance() {
        return SingletonInstance.INSTANCE;
    }

    //这里主要完成初始化工作
    public void init(@NonNull Context context, @NonNull ICrashCallback crashCallback) {
        init(context, crashCallback, null);
    }

    public void init(@NonNull Context context, @NonNull ICrashCallback crashCallback, @Nullable ICrashConfig crashConfig) {

        Thread.setDefaultUncaughtExceptionHandler(this);//将当前实例设为系统默认的异常处理器

        this.mCrashCallback = crashCallback;
        this.mContext = context.getApplicationContext();

        if (crashConfig == null)
            crashConfig = new DefaultCrashConfig();

        this.mCrashConfig = crashConfig;
    }


    public DefaultUncaughtHandler setCrashConfig(@NonNull ICrashConfig crashConfig) {
        this.mCrashConfig = crashConfig;
        return this;
    }

    public DefaultUncaughtHandler setCrashCallback(@NonNull ICrashCallback mCrashCallback) {
        this.mCrashCallback = mCrashCallback;
        return this;
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        //打印出当前调用栈信息
        ex.printStackTrace();

        //如果重写了异常处理，否则就由我们自己结束自己
        if (mCrashConfig != null && mCrashConfig.getReportMode()) {
            try {
                //导出异常信息到SD卡中
                File logFile = dumpExceptionToSDCard(ex);
                //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
                mCrashConfig.uploadExceptionToServer(logFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mCrashCallback != null) {
            mCrashCallback.onCrash(thread, ex);
        } else {
            Process.killProcess(Process.myPid());//结束进程
        }
    }


    /**
     * 持久化异常信息
     *
     * @param ex
     * @return
     * @throws IOException
     */
    private File dumpExceptionToSDCard(Throwable ex) throws IOException {

        File logFile = null;

        //如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File dir = new File(mCrashConfig.getFilePath());
            if (!dir.exists() || dir.isFile()) {
                dir.mkdirs();
            }

            //获取奔溃时间（格式化）
            String crashTime = getCrashTime();

            //以当前时间创建log文件
            logFile = new File(dir, mCrashConfig.getFileNamePrefix() + crashTime + mCrashConfig.getFileNameSuffix());

            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));

                //导出发生异常的时间
                pw.println(crashTime);

                //导出手机信息
                dumpPhoneInfo(pw);

                pw.println();

                //导出异常的调用栈信息
                ex.printStackTrace(pw);

                pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return logFile;
    }

    /**
     * 获取奔溃时间
     *
     * @return
     */
    private String getCrashTime() {
        String crashTime = "";
        long current = System.currentTimeMillis();
        try {
            if (StringUtils.isNonNull(mCrashConfig.getDateFormat())) {
                crashTime = new SimpleDateFormat(mCrashConfig.getDateFormat(), Locale.CHINA).format(new Date(current));
            }
        } catch (Exception e) {
            e.printStackTrace();
            crashTime = new SimpleDateFormat(GlobalConfig.FILE_DATE_FORMAT, Locale.CHINA).format(new Date(current));
        }
        return crashTime;
    }

    /**
     * 持久化异常信息
     *
     * @param printWriter
     * @throws NameNotFoundException
     */
    private void dumpPhoneInfo(PrintWriter printWriter) throws NameNotFoundException {

        printWriter.append("================Build================\n");
        printWriter.append(String.format("BOARD\t%s\n", Build.BOARD));
        printWriter.append(String.format("BOOTLOADER\t%s\n", Build.BOOTLOADER));
        printWriter.append(String.format("BRAND\t%s\n", Build.BRAND));
        printWriter.append(String.format("CPU_ABI\t%s\n", Build.CPU_ABI));
        printWriter.append(String.format("CPU_ABI2\t%s\n", Build.CPU_ABI2));
        printWriter.append(String.format("DEVICE\t%s\n", Build.DEVICE));
        printWriter.append(String.format("DISPLAY\t%s\n", Build.DISPLAY));
        printWriter.append(String.format("FINGERPRINT\t%s\n", Build.FINGERPRINT));
        printWriter.append(String.format("HARDWARE\t%s\n", Build.HARDWARE));
        printWriter.append(String.format("HOST\t%s\n", Build.HOST));
        printWriter.append(String.format("ID\t%s\n", Build.ID));
        printWriter.append(String.format("MANUFACTURER\t%s\n", Build.MANUFACTURER));
        printWriter.append(String.format("MODEL\t%s\n", Build.MODEL));
        printWriter.append(String.format("SERIAL\t%s\n", Build.SERIAL));
        printWriter.append(String.format("PRODUCT\t%s\n", Build.PRODUCT));
        printWriter.append("================APP================\n");

        //应用的版本名称和版本号
        if (mContext != null) {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            printWriter.append(String.format("versionCode\t%s\n", versionCode));
            printWriter.append(String.format("versionName\t%s\n", versionName));
        }
        printWriter.append("================Exception================\n");
    }

}
