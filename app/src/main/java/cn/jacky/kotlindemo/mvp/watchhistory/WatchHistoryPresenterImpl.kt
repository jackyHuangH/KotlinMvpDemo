package cn.jacky.kotlindemo.mvp.watchhistory

import android.annotation.SuppressLint
import android.util.Log
import cn.jacky.kotlindemo.model.local.ACacheModel
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
import com.zenchn.apilib.callback.rx.RxSchedulerController
import cn.jacky.kotlindemo.api.bean.HomeBean
import io.reactivex.Observable
import java.util.*

/**
 * @author:Hzj
 * @date  :2018/12/18/018
 * desc  ：
 * record：
 */
class WatchHistoryPresenterImpl(mView: WatchHistoryContract.View?) : BasePresenterImpl<WatchHistoryContract.View>(mView), WatchHistoryContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getWatchHistory() {
        ACacheModel.getWatchHistoryCacheObservable()
                .flatMap {
                    //按照观看时间排序
                    Collections.sort(it, object : Comparator<HomeBean.Issue.Item> {
                        override fun compare(o1: HomeBean.Issue.Item, o2: HomeBean.Issue.Item): Int {
                            //按照观看时间先后排序
                            return if (o1.watchData < o2.watchData) {
                                1
                            } else {
                                -1
                            }
                        }
                    })
                    Observable.just(it)
                }
                .compose(RxSchedulerController.applySchedulers())
                .subscribe({
                    mView?.setHomeNewData(it)
                }, {
                    mView?.showMessage("获取观看记录失败")
                    Log.e("获取观看记录", "获取观看记录失败：", it)
                })
    }
}