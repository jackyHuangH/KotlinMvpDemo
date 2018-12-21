package cn.jacky.kotlindemo.mvp.videodetail

import android.app.Activity
import cn.jacky.kotlindemo.model.ACacheModel
import cn.jacky.kotlindemo.model.ContextModel
import cn.jacky.kotlindemo.model.VideoDetailModel
import cn.jacky.kotlindemo.model.impl.VideoDetailModelImpl
import cn.jacky.kotlindemo.mvp.basepresenter.BasePresenterImpl
import cn.jacky.kotlindemo.util.dataFormat
import com.zenchn.apilib.entity.HomeBean
import com.zenchn.support.kit.AndroidKit
import com.zenchn.support.kit.NetworkUtils

/**
 * @author:Hzj
 * @date  :2018/12/19/019
 * desc  ：
 * record：
 */
class VideoDetailPresenterImpl(mView: VideoDetailContract.View?) : BasePresenterImpl<VideoDetailContract.View>(mView), VideoDetailContract.Presenter, VideoDetailModel.RelatedVideoListCallback {

    private val mVideoDetailModelImpl by lazy { VideoDetailModelImpl() }

    override fun getVideoDetail(itemInfo: HomeBean.Issue.Item) {
        val playInfo = itemInfo.data?.playInfo
        if (playInfo!!.size > 1) {
            //有多个视频源
            if (NetworkUtils.isWifiConnected(ContextModel.getApplicationContext())) {
                //WiFi状态，播放高清
                for (it in playInfo) {
                    if (it.type == "high") {
                        mView?.setVideo(it.url)
                        break
                    }
                }
            } else {
                //移动网络，播放标清
                for (it in playInfo) {
                    if (it.type == "normal") {
                        mView?.setVideo(it.url)
                        mView?.showMessage("本次播放预计消耗${(mView as Activity).dataFormat(it.urlList[0].size)}流量")
                        break
                    }
                }
            }
        } else {
            //单个视频源
            mView?.setVideo(itemInfo.data!!.playUrl)
        }

        //设置背景
        val backgroundUrl = itemInfo.data!!.cover.blurred +
                "/thumbnail/${AndroidKit.Dimens.getScreenHeight() - AndroidKit.Dimens.dp2px(250)}x${AndroidKit.Dimens.getScreenWidth()}"
        mView?.setBackground(backgroundUrl)
        //设置视频详情
        mView?.setVideoInfo(itemInfo)
    }

    override fun getRelatedVideoList(id: Long) {
        mVideoDetailModelImpl.getRelatedVideoList(id, this)
    }

    override fun onGetRelatedVideoListSuccess(issue: HomeBean.Issue) {
        mView?.setRecentRelatedVideo(issue.itemList)
    }

    override fun saveWatchHistoryCache(item: HomeBean.Issue.Item) {
        //保存观看时的时间，便于后面排序
        item.watchData = System.currentTimeMillis()
        ACacheModel.saveWatchHistoryCache(item)
    }

}