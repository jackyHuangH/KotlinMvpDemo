package cn.jacky.kotlindemo.mvp.userinfo

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.R.id.*
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.util.StatusBarUtil
import com.gyf.barlibrary.ImmersionBar
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.zenchn.support.kit.AndroidKit
import com.zenchn.support.router.Router
import kotlinx.android.synthetic.main.activity_user_info.*
import java.util.*

/**
 * @author:Hzj
 * @date  :2019/1/2/002
 * desc  ：个人主页
 * record：
 */
class UserInfoActivity : BaseActivity() {

    //记录y方向偏移量
    private var mOffsetY = 0
    private var mScrollY = 0

    private val mWebUrl: String = "https://xuhaoblog.com/KotlinMvp"

    override fun getLayoutRes(): Int = R.layout.activity_user_info

    override fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .fitsSystemWindows(false)
                .transparentStatusBar()
                .statusBarDarkFont(true)
        mImmersionBar.init()

        //处理toolbar 与状态栏的间距
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    override fun initWidget() {
        initSwipeRefreshLayout()
        initScrollView()
        initWebView()
        buttonBarLayout.alpha = 0f
        toolbar.setBackgroundColor(0)
        //返回
        toolbar.setNavigationOnClickListener { onBackPressed() }

        smart_refresh.setOnRefreshListener { webView.loadUrl(mWebUrl) }
        smart_refresh.autoRefresh()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
                view.loadUrl(request?.url.toString())
                return true
            }

            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                smart_refresh.finishRefresh()
                view.loadUrl(String.format(Locale.CHINA, "javascript:document.body.style.paddingTop='%dpx'; void 0",
                        AndroidKit.Dimens.px2dp(webView.paddingTop)))
            }
        }
    }

    private fun initScrollView() {
        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h = AndroidKit.Dimens.dp2px(170)
            private val color = ContextCompat.getColor(this@UserInfoActivity, R.color.colorPrimary) and 0x00ffffff
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                var tScrollY = scrollY
                if (lastScrollY < h) {
                    tScrollY = Math.min(h, tScrollY)
                    mScrollY = if (tScrollY > h) h else tScrollY
                    buttonBarLayout.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    iv_parallax.translationY = (mOffsetY - mScrollY).toFloat()
                }
                lastScrollY = tScrollY
            }
        })
    }

    private fun initSwipeRefreshLayout() {
        smart_refresh.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                mOffsetY = offset / 2
                //垂直方向移动背景图片
                iv_parallax.translationY = (mOffsetY - mScrollY).toFloat()
                //设置toolbar透明度
                toolbar.alpha = 1 - Math.min(percent, 1f)
            }
        })
    }

    companion object {
        fun launch(from: Activity) {
            Router
                    .newInstance()
                    .from(from)
                    .to(UserInfoActivity::class.java)
                    .launch()
        }
    }
}