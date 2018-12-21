package cn.jacky.kotlindemo.mvp.videodetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.adapter.VideoDetailListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.util.durationFormat
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import com.orhanobut.logger.Logger
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.zenchn.apilib.entity.HomeBean
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.recycle_header_video_detail.*

/**
 * @author:Hzj
 * @date  :2018/12/19/019
 * desc  ：
 * record：
 */

/**
 * 跳转详情带过来的数据
 */
private const val EXTRA_VIDEO_DATA = "EXTRA_VIDEO_DATA"

class VideoDetailActivity : BaseActivity(), VideoDetailContract.View, BaseQuickAdapter.OnItemClickListener {

    object Const {
        const val TRANSITION_NAME = "IMG_VIDEO_TRANSITION"
    }

    private val mPresenterImpl by lazy {
        VideoDetailPresenterImpl(this)
    }

    //是否在播放中
    private var mIsPlaying: Boolean = false
    private var mHasPaused: Boolean = false

    private lateinit var mVideoData: HomeBean.Issue.Item
    private var mListAdapter: VideoDetailListAdapter? = null
    private lateinit var mOrientationUtils: OrientationUtils

    override fun getLayoutRes(): Int = R.layout.activity_video_detail

    override fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .fitsSystemWindows(false)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
        mImmersionBar.init()
    }

    override fun initWidget() {
        mVideoData = intent.getSerializableExtra(EXTRA_VIDEO_DATA) as HomeBean.Issue.Item
        initSwipeRefresh()
        initEnterTransition()
        initVideoPlayer()
        initRecycleView()
    }

    private fun initSwipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            loadVideoData()
        }
    }

    //获取视频信息
    private fun loadVideoData() {
        //获取视频
        mPresenterImpl.getVideoDetail(mVideoData)
        //获取相关视频列表
        mPresenterImpl.getRelatedVideoList(mVideoData.data?.id ?: 0)
    }

    private fun initRecycleView() {
        rlv_video_detail.layoutManager = LinearLayoutManager(this)
        val list = ArrayList<HomeBean.Issue.Item>()
        mListAdapter = VideoDetailListAdapter(list)
        val header = LayoutInflater.from(this).inflate(R.layout.recycle_header_video_detail, rlv_video_detail, false)
        mListAdapter?.addHeaderView(header)
        val footer = LayoutInflater.from(this).inflate(R.layout.recycle_footer_no_more, rlv_video_detail, false)
        mListAdapter?.addFooterView(footer)
        mListAdapter?.onItemClickListener = this
        rlv_video_detail.adapter = mListAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        //加载视频详情
        val item = adapter.getItem(position) as HomeBean.Issue.Item
        //获取视频
        mPresenterImpl.getVideoDetail(item)
    }

    private fun initVideoPlayer() {
        //设置旋转工具类
        mOrientationUtils = OrientationUtils(this, gsy_player)
        //设置是否自动旋转
        gsy_player.isRotateViewAuto = false
        //是否可以滑动调整
        gsy_player.setIsTouchWiget(true)
        //添加封面
        val thumbCover = ImageView(this)
        thumbCover.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideApp
                .with(this)
                .load(mVideoData.data?.cover?.feed)
                .centerCrop()
                .into(thumbCover)
        gsy_player.thumbImageView = thumbCover
        //设置回调
        gsy_player.setVideoAllCallBack(object : GSYSampleCallBack() {

            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                //开始播放了才能全屏
                mOrientationUtils.isEnable = true
                mIsPlaying = true
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)
                Logger.d("***** onAutoPlayComplete **** ")
            }

            override fun onPlayError(url: String?, vararg objects: Any?) {
                super.onPlayError(url, *objects)
                showMessage("哎呀，播放失败~")
            }

            override fun onEnterFullscreen(url: String?, vararg objects: Any?) {
                super.onEnterFullscreen(url, *objects)
                Logger.d("***** onEnterFullscreen **** ")
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                initStatusBar()
                Logger.d("***** onQuitFullscreen **** ")
                //列表返回的样式判断
                mOrientationUtils.backToProtVideo()
            }

            override fun onClickBlank(url: String?, vararg objects: Any?) {
                super.onClickBlank(url, *objects)
                //点击空白区域
                initStatusBar()
            }
        })
        //设置返回按钮
        gsy_player.backButton.setOnClickListener { onBackPressed() }
        //全屏按钮
        gsy_player.fullscreenButton.setOnClickListener {
            //直接横屏
            mOrientationUtils.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusBar
            gsy_player.startWindowFullscreen(this, true, true)
        }
        //锁屏时间
        gsy_player.setLockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            mOrientationUtils.isEnable = !lock
        }
    }

    /**
     * 初始化进入转换效果
     */
    private fun initEnterTransition() {
        postponeEnterTransition()
        ViewCompat.setTransitionName(gsy_player, Const.TRANSITION_NAME)
        val enterTransition = window.sharedElementEnterTransition
        enterTransition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                loadVideoData()
                enterTransition?.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

        })
        startPostponedEnterTransition()
    }


    override fun setVideo(url: String) {
        //开启自动播放,默认不缓存
        gsy_player.setUp(url, false, "")
        gsy_player.startPlayLogic()
    }

    @SuppressLint("SetTextI18n")
    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        if (swipe_refresh.isRefreshing) {
            swipe_refresh.isRefreshing = false
        }

        //保存观看记录
        mPresenterImpl.saveWatchHistoryCache(itemInfo)

        //视频相关信息
        tv_video_title.text = itemInfo.data?.title
        tv_video_tag.text = "#${itemInfo.data?.category} / ${durationFormat(itemInfo.data?.duration)}"
        tv_video_desc.text = itemInfo.data?.description
        tv_action_favorites.text = itemInfo.data?.consumption?.collectionCount.toString()
        tv_action_share.text = itemInfo.data?.consumption?.shareCount.toString()
        tv_action_reply.text = itemInfo.data?.consumption?.replyCount.toString()

        //作者相关
        if (itemInfo.data?.author != null) {
            val author = itemInfo.data?.author!!
            tv_author_name.text = author.name
            tv_author_desc.text = author.description

            GlideApp
                    .with(this)
                    .load(author.icon)
                    .placeholder(R.drawable.default_avatar)
                    .circleCrop()
                    .into(iv_author_avatar)
        } else {
            layout_author_view.visibility = View.GONE
        }

        //点击事件，目前先不做
        tv_action_favorites.setOnClickListener {
            showMessage("后续添加~")
        }
        tv_action_share.setOnClickListener {
            showMessage("后续添加~")
        }
        tv_action_reply.setOnClickListener {
            showMessage("后续添加~")
        }
        tv_action_offline.setOnClickListener {
            showMessage("后续添加~")
        }
        tv_attention_author.setOnClickListener {
            showMessage("后续添加~")
        }
    }

    override fun setBackground(url: String) {
        //设置背景图片,模糊效果
        GlideApp.with(this)
                .load(url)
                .centerCrop()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_list_bg)
    }

    /**
     * 展示相关视频列表
     */
    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        mListAdapter?.setNewData(itemList)
    }

    //------------------------------------gsy player 生命周期绑定-----------------------------------------------

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (mIsPlaying && !mHasPaused) {
            gsy_player.onConfigurationChanged(this, newConfig, mOrientationUtils)
        }
    }


    override fun onBackPressed() {
        mOrientationUtils.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        mOrientationUtils.releaseListener()
        super.onBackPressed()
    }

    override fun onResume() {
        getCurPlay().onVideoResume()
        super.onResume()
        mHasPaused = false
    }

    override fun onPause() {
        getCurPlay().onVideoPause()
        super.onPause()
        mHasPaused = true
    }

    override fun onDestroy() {
        super.onDestroy()
        //释放资源
        if (mIsPlaying) {
            gsy_player.currentPlayer.release()
        }
        mOrientationUtils.releaseListener()
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (gsy_player.fullWindowPlayer != null) {
            gsy_player.fullWindowPlayer
        } else gsy_player
    }

    //------------------------------------------------------------------------------

    companion object {
        fun launch(from: Activity, view: View, data: HomeBean.Issue.Item) {
            val intent = Intent(from, VideoDetailActivity::class.java)
            intent.putExtra(EXTRA_VIDEO_DATA, data)
            //定制跳转场景
            val pair = Pair(view, Const.TRANSITION_NAME)
            val sceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(from, pair)
            ActivityCompat.startActivity(from, intent, sceneTransitionAnimation.toBundle())
        }
    }
}