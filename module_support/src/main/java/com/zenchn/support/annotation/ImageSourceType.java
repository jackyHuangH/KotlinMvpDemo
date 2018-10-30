package com.zenchn.support.annotation;

import android.support.annotation.IntDef;

import static com.zenchn.support.annotation.ImageSourceType.FILE;
import static com.zenchn.support.annotation.ImageSourceType.INVALID;
import static com.zenchn.support.annotation.ImageSourceType.PATH;
import static com.zenchn.support.annotation.ImageSourceType.RES_ID;
import static com.zenchn.support.annotation.ImageSourceType.URI;
import static com.zenchn.support.annotation.ImageSourceType.URL;

/**
 * @author:Hzj
 * @date :2018/9/11/011
 * desc  ：图片资源类型
 * record：
 */
@IntDef({INVALID, RES_ID, PATH, URL, FILE, URI})
public @interface ImageSourceType {
    int INVALID = 0;//无效资源
    int RES_ID = 1;// Res资源
    int PATH = 2;// 文件
    int URL = 3;// 网络
    int FILE = 4;// 文件
    int URI = 5;// 统一资源标识
}
