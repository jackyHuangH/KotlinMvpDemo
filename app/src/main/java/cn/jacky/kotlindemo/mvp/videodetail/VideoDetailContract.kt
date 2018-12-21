package cn.jacky.kotlindemo.mvp.videodetail

import cn.jacky.kotlindemo.mvp.basepresenter.IPresenter
import cn.jacky.kotlindemo.mvp.baseview.IView
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/19/019
 * desc  ：
 * record：
 */
interface VideoDetailContract {

    interface View : IView {
        /**
         * 设置视频播放源
         */
        fun setVideo(url: String)

        /**
         * 设置视频信息
         */
        fun setVideoInfo(itemInfo: HomeBean.Issue.Item)

        /**
         * 设置背景
         */
        fun setBackground(url: String)

        /**
         * 设置最新相关视频
         */
        fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)

    }

    interface Presenter : IPresenter {
        /**
         * 获取视频详情信息
         */
        fun getVideoDetail(itemInfo: HomeBean.Issue.Item)

        /**
         * 获取相关视频列表
         */
        fun getRelatedVideoList(id: Long)

        /**
         * 保存观看记录
         */
        fun saveWatchHistoryCache(item: HomeBean.Issue.Item)
    }
}