package com.huangzj.kotlindemo.wrapper.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.youth.banner.loader.ImageLoader

/**
 * @author:Hzj
 * @date  :2018/12/14/014
 * desc  ：banner imageLoader
 * record：
 */
class GlideImageLoader : ImageLoader() {

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        GlideApp
                .with(context!!)
                .load(path)
                .transform(RoundedCorners(5))
                .into(imageView!!)
    }
}