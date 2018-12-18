package cn.jacky.kotlindemo.wrapper.glide

import android.support.annotation.Nullable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Pattern

/**
 * @author:Hzj
 * @date  :2018/12/13/013
 * desc  ：
 * record：
 */
class CustomBaseGlideUrlLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>,
                               @Nullable modelCache: ModelCache<String, GlideUrl>) : BaseGlideUrlLoader<String>(concreteLoader, modelCache) {

    companion object {
        private val urlCache = ModelCache<String, GlideUrl>(150)
        /**
         * Url的匹配规则
         */
        private val PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__")
    }

    /**
     * If the URL contains a special variable width indicator (eg "__w-200-400-800__")
     * we get the buckets from the URL (200, 400 and 800 in the example) and replace
     * the URL with the best bucket for the requested width (the bucket immediately
     * larger than the requested width).
     *
     * 控制加载的图片的大小
     */
    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        var matcher = PATTERN.matcher(model)
        var bestBucket = 0
        if (matcher.find()) {
            var found = matcher.group(1).split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (bucketStr in found) {
                bestBucket = Integer.parseInt(bucketStr)
                if (bestBucket >= width) {
                    // the best bucket is the first immediately bigger than the requested width
                    break
                }
            }
        }
        return model
    }

    override fun handles(model: String): Boolean {
        return true
    }

    /**
     * 工厂模式构建对象
     */
    class Factory : ModelLoaderFactory<String, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return CustomBaseGlideUrlLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java), urlCache)
        }

        override fun teardown() {
        }
    }
}