package cn.jacky.kotlindemo.model

import android.annotation.SuppressLint
import android.util.Log
import cn.jacky.kotlindemo.model.CacheKey.KEY_WATCH_HISTORY
import com.zenchn.apilib.callback.rx.RxSchedulerController
import com.zenchn.apilib.entity.HomeBean
import com.zenchn.support.cache.ACache
import io.reactivex.Observable

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：ACache 缓存工具类封装
 * record：
 */
object CacheKey {
    //观看历史key
    const val KEY_WATCH_HISTORY = "WATCH_HISTORY"
}

object ACacheModel {
    private const val TAG = "ACacheModel"

    private val mACache by lazy {
        ACache.get(ContextModel.getApplicationContext())
    }

    //------------------缓存视频观看记录相关-----------------------------
    /**
     * 保存观看记录
     * map<itemId,item>
     */
    @SuppressLint("CheckResult")
    fun saveWatchHistoryCache(item: HomeBean.Issue.Item) {
        Observable.just(item)
                .map {
                    val cachedSet = getWatchHistoryCache() ?: HashSet<HomeBean.Issue.Item>()
                    cachedSet.add(it)
                    mACache.put(KEY_WATCH_HISTORY, cachedSet)
                }
                .compose(RxSchedulerController.applySchedulers())
                .subscribe({
                    Log.d(TAG, "保存观看记录成功")
                }, {
                    Log.e(TAG, "保存观看记录失败：", it)
                })
    }

    /**
     * 取出观看记录
     */
    fun getWatchHistoryCache(): HashSet<HomeBean.Issue.Item>? {
        val obj = mACache.getAsObject(KEY_WATCH_HISTORY)
        return if (obj == null) {
            HashSet<HomeBean.Issue.Item>()
        } else {
            obj as HashSet<HomeBean.Issue.Item>
        }
    }
}