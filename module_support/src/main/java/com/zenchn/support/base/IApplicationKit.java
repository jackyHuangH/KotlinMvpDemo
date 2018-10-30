package com.zenchn.support.base;

import android.app.Application;

/**
 * 作    者：wangr on 2017/3/9 16:24
 * 描    述：
 * 修订记录：
 */
public interface IApplicationKit {

    void initKit(Application application);

    Application getApplication();

    void initSetting();

}
