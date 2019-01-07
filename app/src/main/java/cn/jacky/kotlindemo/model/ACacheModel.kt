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
    fun saveWatchHistoryCache(newItem: HomeBean.Issue.Item) {
        Observable.just(getWatchHistoryCache())
                .map {
                    Log.d(TAG, "之前保存的记录：" + it)
                    val iterator = it.iterator()
                    while (iterator.hasNext()) {
                        val oldItem = iterator.next()
                        //保存过的就先删除原来的，再覆盖保存
                        if (oldItem.data?.id == newItem.data?.id) {
                            iterator.remove()
                            break
                        }
                    }
                    it.add(newItem)
                    Log.d(TAG, "现在保存的记录：" + it)
                    mACache.put(KEY_WATCH_HISTORY, it)
                }
                .compose(RxSchedulerController.applySchedulers())
                .subscribe({
                    Log.d(TAG, "保存观看记录成功")
                }, {
                    Log.e(TAG, "保存观看记录失败：", it)
                })
    }

    /**
     * 取出观看记录,这里是同步方式，调用时务必放在子线程
     */
    fun getWatchHistoryCache(): ArrayList<HomeBean.Issue.Item> {
        val obj = mACache.getAsObject(KEY_WATCH_HISTORY)
        return if (obj is ArrayList<*>) {
            obj as ArrayList<HomeBean.Issue.Item>
        } else {
            ArrayList()
        }
    }

    /**
     * 取出观看记录，被观察者
     */
    fun getWatchHistoryCacheObservable(): Observable<ArrayList<HomeBean.Issue.Item>> {
        return Observable.just(getWatchHistoryCache())
    }
}