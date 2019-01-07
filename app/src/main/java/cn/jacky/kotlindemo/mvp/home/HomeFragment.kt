package cn.jacky.kotlindemo.mvp.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.R.id.*
import cn.jacky.kotlindemo.mvp.adapter.HomeListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment
import cn.jacky.kotlindemo.mvp.search.SearchActivity
import cn.jacky.kotlindemo.mvp.videodetail.VideoDetailActivity
import cn.jacky.kotlindemo.util.StatusBarUtil
import cn.jacky.kotlindemo.wrapper.glide.GlideImageLoader
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.youth.banner.BannerConfig
import com.zenchn.apilib.entity.HomeBean
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.recycle_header_banner_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：每日精选
 * record：
 */
class HomeFragment : BaseFragment(), HomeContract.View, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    private var mTitle: String? = null
    private var mHomeAdapter: HomeListAdapter? = null

    private val mLinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val mSimpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    private var num: Int = 1
    private val mPresenter by lazy { HomePresenterImpl(this) }

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initWidget() {
        initRefreshLayout()
        initRecyclerView()
        initToolBar()
        initSearchBar()
    }

    private fun initSearchBar() {
        ib_search.setOnClickListener {
            // 跳转搜索,1定义场景
            val optionsCompat = activity?.let { ActivityOptionsCompat.makeSceneTransitionAnimation(it, ib_search, ib_search.transitionName) }
            startActivity(Intent(activity, SearchActivity::class.java), optionsCompat?.toBundle())
        }
    }

    private fun initToolBar() {
        toolbar.setOnClickListener {
            rlv.smoothScrollToPosition(0)
        }
    }

    override fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .fitsSystemWindows(false)
                .transparentStatusBar()
                .statusBarDarkFont(true)
        mImmersionBar.init()

        //处理toolbar 与状态栏的间距
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
    }

    override fun lazyLoad() {
        //刷新数据
        mPresenter.refreshHomeData(num)
        swipe_refresh.isRefreshing = true
    }

    private fun initRecyclerView() {
        rlv.layoutManager = mLinearLayoutManager
        rlv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //监听列表滑动，改变toobar透明度
                val firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition()
                autoChangeToolbar(firstVisibleItemPosition)
            }
        })

        val emptyList: ArrayList<HomeBean.Issue.Item> = ArrayList()
        mHomeAdapter = activity?.let { HomeListAdapter(emptyList) }
        mHomeAdapter?.setOnLoadMoreListener(this, rlv)
        val header = LayoutInflater.from(activity).inflate(R.layout.recycle_header_banner_home, rlv, false)
        mHomeAdapter?.addHeaderView(header)
        mHomeAdapter?.onItemClickListener = this
        rlv.adapter = mHomeAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        // 跳转视频详情
        val item = adapter.getItem(position) as HomeBean.Issue.Item
        activity?.let { VideoDetailActivity.launch(it, view!!, item) }
    }

    /**
     * 动态改变toolbar颜色和透明度
     */
    private fun autoChangeToolbar(firstPosition: Int) {
        if (firstPosition == 0) {
            //toolbar全透明
            toolbar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.transparent))
            ib_search.setImageResource(R.drawable.ic_action_search_white)
            tv_header_title.text = ""
        } else {
            if (mHomeAdapter?.data!!.size > 1) {
                toolbar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.color_title_bg))
                ib_search.setImageResource(R.drawable.ic_action_search_black)
                val list = mHomeAdapter!!.data
                val currItem = list[firstPosition - 1]
                if (currItem.type == "textHeader") {
                    tv_header_title.text = currItem.data?.text
                } else {
                    tv_header_title.text = mSimpleDateFormat.format(currItem.data?.date)
                }
            }
        }
    }

    private fun initRefreshLayout() {
        swipe_refresh.setColorSchemeResources(R.color.black)
        swipe_refresh.setOnRefreshListener {
            //刷新数据
            mPresenter.refreshHomeData(num)
        }
    }

    override fun onLoadMoreRequested() {
        mPresenter.loadMoreData()
    }

    override fun showBannerList(bannerItems: ArrayList<HomeBean.Issue.Item>, bannerUrls: ArrayList<String>, bannerTitles: ArrayList<String>) {
        //填充banner
        banner_home.setImageLoader(GlideImageLoader())
        banner_home.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner_home.setImages(bannerUrls)
        banner_home.setBannerTitles(bannerTitles)
        banner_home.setOnBannerListener { view, position ->
            //跳转视频详情
            val item = bannerItems[position]
            activity?.let { VideoDetailActivity.launch(it, view, item) }
        }
        banner_home.start()
    }

    override fun setHomeNewData(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        mHomeAdapter?.setNewData(itemList)
        setLoadStatus(hasNextPage)
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        mHomeAdapter?.addData(itemList)
        setLoadStatus(hasNextPage)
    }

    private fun setLoadStatus(hasNextPage: Boolean) {
        if (swipe_refresh.isRefreshing) {
            swipe_refresh.isRefreshing = false
        }
        if (hasNextPage) {
            mHomeAdapter?.loadMoreComplete()
        } else {
            mHomeAdapter?.loadMoreEnd()
        }
        mHomeAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        banner_home?.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner_home?.stopAutoPlay()
    }
}