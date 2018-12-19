package cn.jacky.kotlindemo.mvp.videodetail

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.adapter.VideoDetailListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.chad.library.adapter.base.BaseQuickAdapter
import com.orhanobut.logger.Logger
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.zenchn.apilib.entity.HomeBean
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.util.*

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

    private var mVideoData: HomeBean.Issue.Item? = null
    private var mListAdapter: VideoDetailListAdapter? = null

    override fun getLayoutRes(): Int = R.layout.activity_video_detail

    override fun initWidget() {
        initEnterTransition()
        initVideoPlayer()
        initRecycleView()
    }

    //获取视频信息
    private fun loadVideoData() {
        mVideoData = intent.getSerializableExtra(EXTRA_VIDEO_DATA) as HomeBean.Issue.Item
        //todo 保存观看记录
        //todo 获取视频
    }

    private fun initRecycleView() {
        rlv_video_detail.layoutManager=LinearLayoutManager(this)
        val list=ArrayList<HomeBean.Issue.Item>()
        mListAdapter = VideoDetailListAdapter(list)
        mListAdapter?.onItemClickListener = this

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        //todo 跳转视频详情
    }

    private fun initVideoPlayer() {
        //设置旋转工具类
        val orientationUtils = OrientationUtils(this, gsy_player)
        //设置是否自动旋转
        gsy_player.isRotateViewAuto = false
        //是否可以滑动调整
        gsy_player.setIsTouchWiget(true)
        //添加封面
        val thumbCover = ImageView(this)
        GlideApp
                .with(this)
                .load(mVideoData?.data?.cover?.feed)
                .centerCrop()
                .into(thumbCover)
        gsy_player.thumbImageView = thumbCover
        //设置回调
        gsy_player.setVideoAllCallBack(object : GSYSampleCallBack() {

            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                //开始播放了才能全屏
                orientationUtils.isEnable = true
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
                Logger.d("***** onQuitFullscreen **** ")
                //列表返回的样式判断
                orientationUtils.backToProtVideo()
            }
        })
        //设置返回按钮
        gsy_player.backButton.setOnClickListener { onBackPressed() }
        //全屏按钮
        gsy_player.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusBar
            gsy_player.startWindowFullscreen(this, true, true)
        }
        //锁屏时间
        gsy_player.setLockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils.isEnable = !lock
        }
    }

    /**
     * 初始化进入转换效果
     */
    private fun initEnterTransition() {
        postponeEnterTransition()
        ViewCompat.setTransitionName(gsy_player, Const.TRANSITION_NAME)
        window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                transition?.removeListener(this)
                loadVideoData()
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