package cn.jacky.kotlindemo.mvp.classifydetail

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.api.bean.CategoryBean
import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.mvp.adapter.ClassifyDetailListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.mvp.videodetail.VideoDetailActivity
import cn.jacky.kotlindemo.util.StatusBarUtil
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.zenchn.support.router.Router
import com.zenchn.support.widget.itemdecoration.VerticalItemDecoration
import kotlinx.android.synthetic.main.activity_classify_detail.*

/**
 * @author:Hzj
 * @date  :2018/12/25/025
 * desc  ：分类详情
 * record：
 */
class ClassifyDetailActivity : BaseActivity(), ClassifyDetailContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    private val mPresenter by lazy { ClassifyDetailPresenterImpl(this) }

    private var mCategoryBean: CategoryBean? = null
    private lateinit var mDetailListAdapter: ClassifyDetailListAdapter

    object CollapsingToolbarLayoutState {
        const val EXPANDED = 0
        const val COLLAPSED = 1
        const val INTERNEDIATE = 2
    }

    //标记当前appbar的舒展状态
    private var mCurrExpendState = CollapsingToolbarLayoutState.EXPANDED

    override fun getLayoutRes(): Int = R.layout.activity_classify_detail

    override fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .fitsSystemWindows(false)
                .transparentStatusBar()
                .statusBarDarkFont(false)
        mImmersionBar.init()
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    @SuppressLint("SetTextI18n")
    override fun initWidget() {
        lifecycle.addObserver(mPresenter)
        mCategoryBean = intent.getSerializableExtra(EXTRA_CATEGORY_BEAN) as CategoryBean
        initToolbar()
        initRecyclerView()
        requestListData()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        //显示返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setOnClickListener { rlv.smoothScrollToPosition(0) }
        toolbar.setNavigationOnClickListener { onBackPressed() }
        mCategoryBean?.let {
            GlideApp.with(this)
                    .load(it.headerImage)
                    .placeholder(R.color.color_darker_gray)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv_cover)

            tv_category_desc.text = "#${it.description}#"
            collapse_bar.title = it.name
            //设置展开状态下标题字体颜色
            collapse_bar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white))
            //设置收起状态下标题字体颜色
            collapse_bar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.black))
        }
        addAppbarListener()
    }

    private fun addAppbarListener() {
        //添加appbarlayout收缩状态监听
        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                if (mCurrExpendState != CollapsingToolbarLayoutState.EXPANDED) {
                    //修改状态标记为展开
                    mCurrExpendState = CollapsingToolbarLayoutState.EXPANDED
                    setTransparentStausbar()
                    toolbar.setNavigationIcon(R.drawable.back_white)

                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                if (mCurrExpendState != CollapsingToolbarLayoutState.COLLAPSED) {
                    //修改状态标记为折叠
                    mCurrExpendState = CollapsingToolbarLayoutState.COLLAPSED
                    setWhiteStatusbar()
                    toolbar.setNavigationIcon(R.drawable.back_black)
                }
            } else {
                if (mCurrExpendState != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (mCurrExpendState == CollapsingToolbarLayoutState.COLLAPSED) {

                    }
                    //修改状态标记为中间
                    mCurrExpendState = CollapsingToolbarLayoutState.INTERNEDIATE
                }
            }
        }
    }

    /**
     * 设置透明状态栏
     */
    private fun setTransparentStausbar() {
        mImmersionBar
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .init()
    }

    /**
     * 设置白色状态栏
     */
    private fun setWhiteStatusbar() {
        mImmersionBar
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .init()
    }

    private fun initRecyclerView() {
        rlv.layoutManager = LinearLayoutManager(this)
        rlv.addItemDecoration(VerticalItemDecoration(this, 1, false))
        val list = ArrayList<HomeBean.Issue.Item>()
        mDetailListAdapter = ClassifyDetailListAdapter(R.layout.recycle_item_classify_detail, list)
        mDetailListAdapter.onItemClickListener = this
        mDetailListAdapter.setOnLoadMoreListener(this, rlv)
        rlv.adapter = mDetailListAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        // 跳转视频详情
        val item = adapter.getItem(position) as HomeBean.Issue.Item
        VideoDetailActivity.launch(this, view!!, item)
    }

    private fun requestListData() {
        mCategoryBean?.let { mPresenter.getCategoryDetailList(it.id) }
    }

    override fun onLoadMoreRequested() {
        mPresenter.loadMoreData()
    }

    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        mDetailListAdapter.setNewData(itemList)
        setLoadStatus(hasNextPage)
    }

    override fun addMoreDetailList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        mDetailListAdapter.addData(itemList)
        setLoadStatus(hasNextPage)
    }

    private fun setLoadStatus(hasNextPage: Boolean) {
        if (hasNextPage) {
            mDetailListAdapter.loadMoreComplete()
        } else {
            mDetailListAdapter.loadMoreEnd()
        }
        mDetailListAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val EXTRA_CATEGORY_BEAN = "EXTRA_CATEGORY_BEAN"

        fun launch(from: Activity, category: CategoryBean) {
            Router
                    .newInstance()
                    .putSerializable(EXTRA_CATEGORY_BEAN, category)
                    .from(from)
                    .to(ClassifyDetailActivity::class.java)
                    .launch()
        }
    }
}