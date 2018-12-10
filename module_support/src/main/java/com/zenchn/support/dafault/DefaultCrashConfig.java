package com.zenchn.support.dafault;


import com.zenchn.support.GlobalConfig;
import com.zenchn.support.base.ICrashConfig;

import java.io.File;

/**
 * 作    者：wangr on 2017/5/4 10:50
 * 描    述：异常信息处理者（自定义处理）
 * 修订记录：
 */
public class DefaultCrashConfig implements ICrashConfig {

    @Override
    public boolean getReportMode() {
        return GlobalConfig.isReport;
    }

    @Override
    public String getFilePath() {
        return GlobalConfig.FILE_PATH;
    }

    @Override
    public String getFileNamePrefix() {
        return GlobalConfig.FILE_NAME_PREFIX;
    }

    @Override
    public String getDateFormat() {
        return GlobalConfig.FILE_DATE_FORMAT;
    }

    @Override
    public String getFileNameSuffix() {
        return GlobalConfig.FILE_NAME_SUFFIX;
    }

    @Override
    public void uploadExceptionToServer(File logFile) {
        // Upload Exception Message To Your Web Server.
    }

}
