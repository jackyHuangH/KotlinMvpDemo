package cn.jacky.kotlindemo.mvp.search

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.app.ApplicationKit
import cn.jacky.kotlindemo.mvp.adapter.HotWordFlowAdapter
import cn.jacky.kotlindemo.mvp.adapter.SearchListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.mvp.videodetail.VideoDetailActivity
import cn.jacky.kotlindemo.util.ViewAnimUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.*
import com.jakewharton.rxbinding2.widget.RxTextView
import com.zenchn.apilib.entity.HomeBean
import com.zenchn.support.kit.AndroidKit
import com.zenchn.support.utils.StringUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search.*

/**
 * @author:Hzj
 * @date  :2018/12/17/017
 * desc  ：
 * record：
 */
class SearchActivity : BaseActivity(), SearchContract.View, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private val mPresenter by lazy {
        SearchPresenterImpl(this)
    }

    private var mTextTypeface: Typeface? = null

    init {
        //细黑简体字体
        mTextTypeface = Typeface.createFromAsset(ApplicationKit.instance.application.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    private var mSearchAdapter: SearchListAdapter? = null
    private var num = 1

    private var mHotWordFlowAdapter: HotWordFlowAdapter? = null

    override fun getLayoutRes(): Int = R.layout.activity_search

    override fun initWidget() {
        setupEnterAnimation()
        setupExitAnimation()
        initEt()
        initSearchResultList()
        initHotWordList()
        initListener()
    }

    @SuppressLint("CheckResult")
    private fun initEt() {
        tv_title_tip.typeface = mTextTypeface
        tv_hot_search_words.typeface = mTextTypeface
        RxTextView
                .textChanges(et_search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    ib_cancel.visibility = if (StringUtils.isEmpty(it)) View.GONE else View.VISIBLE
                }
        RxTextView
                .editorActions(et_search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it == EditorInfo.IME_ACTION_SEARCH) {
                        doInitQuery()
                    }
                }
    }

    private fun initListener() {
        ib_cancel.setOnClickListener {
            et_search.text.clear()
        }
        tv_cancel.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        AndroidKit.Keyboard.hideSoftInput(this, et_search)
        ViewAnimUtil.animateRevealHide(this,
                constraint_layout,
                float_bt.width / 2,
                R.color.backgroundColor,
                object : ViewAnimUtil.OnRevealAnimationListener {
                    override fun onRevealShow() {
                    }

                    override fun onRevealHide() {
                        defaultBackPressed()
                    }
                })
    }

    private fun defaultBackPressed() {
        super.onBackPressed()
    }

    /**
     * 退场动画
     */
    private fun setupExitAnimation() {
        val fade = Fade()
        fade.duration = 500
        window.returnTransition = fade
    }

    /**
     * 设置入场动画
     */
    private fun setupEnterAnimation() {
        //2页面进入时的转换效果
        val transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                //展示揭露动画
                animateRevealShow()
            }
        })
    }

    /**
     * 展示揭露动画
     */
    private fun animateRevealShow() {
        ViewAnimUtil.animateRevealShow(this,
                constraint_layout,
                (float_bt.width / 2).toFloat(),
                R.color.backgroundColor,
                object : ViewAnimUtil.OnRevealAnimationListener {
                    override fun onRevealShow() {
                        initView()
                    }

                    override fun onRevealHide() {

                    }
                })
    }

    /**
     * 初始化控件，
     */
    private fun initView() {
        //添加渐变动画
        val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        loadAnimation.duration = 500
        ll_container.startAnimation(loadAnimation)
        ll_container.visibility = View.VISIBLE
        AndroidKit.Keyboard.showSoftInput(this)
        //获取热门搜索
        mPresenter.getHotSearchWord()
    }

    private fun initHotWordList() {
        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP //按正常方向换行
        flexboxLayoutManager.flexDirection = FlexDirection.ROW //主轴在水平方向，起点在左端
        flexboxLayoutManager.alignItems = AlignItems.CENTER //定义item在副轴上如何对齐
        flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START //多个轴的对齐方式
        rlv_hot_word.layoutManager = flexboxLayoutManager
        val list = ArrayList<String>()
        mHotWordFlowAdapter = HotWordFlowAdapter(R.layout.recycle_item_search_hot_word, list)
        mHotWordFlowAdapter?.setOnItemClickListener { adapter, view, position ->
            AndroidKit.Keyboard.hideSoftInput(this@SearchActivity)
            val item = adapter.getItem(position) as String
            mPresenter.searchRequest(item, num)
        }
        rlv_hot_word.adapter = mHotWordFlowAdapter
    }

    private fun initSearchResultList() {
        rlv_result.layoutManager = LinearLayoutManager(this)
        val emptyList: ArrayList<HomeBean.Issue.Item> = ArrayList()
        mSearchAdapter = SearchListAdapter(R.layout.recycle_item_search, emptyList)
        mSearchAdapter?.setOnLoadMoreListener(this, rlv_result)
        mSearchAdapter?.onItemClickListener = this
        rlv_result.adapter = mSearchAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        //跳转视频详情
        val item = adapter.getItem(position) as HomeBean.Issue.Item
        VideoDetailActivity.launch(this, view!!, item)
    }

    /**
     * 初始化搜索
     */
    private fun doInitQuery() {
        val word = et_search.text.toString()
        if (StringUtils.isEmpty(word)) {
            showMessage("请输入你感兴趣的关键字")
            return
        }
        mPresenter.searchRequest(word, num)
        AndroidKit.Keyboard.hideSoftInput(this)
    }

    /**
     * 加载更多
     */
    override fun onLoadMoreRequested() {
        mPresenter.searchLoadMore()
    }

    /**
     * 隐藏热门关键字的 View
     */
    private fun showResultListView() {
        ll_hot_word.visibility = View.GONE
        ll_result_list.visibility = View.VISIBLE
    }

    /**
     * 显示热门关键字的 流式布局
     */
    private fun showHotWordView() {
        ll_hot_word.visibility = View.VISIBLE
        ll_result_list.visibility = View.GONE
    }

    override fun showHotSearchWord(hotWords: ArrayList<String>) {
        showHotWordView()
        mHotWordFlowAdapter?.setNewData(hotWords)
    }

    override fun showNoResult() {
        showMessage("抱歉！未搜到相关内容，换个关键字试试？")
        showHotWordView()
        tv_search_count.visibility = View.GONE
    }

    override fun showResultTotalNum(keyword: String, total: Int) {
        tv_search_count.visibility = View.VISIBLE
        tv_search_count.text = String.format(resources.getString(R.string.search_result_count), keyword, total)
    }

    override fun showNewSearchList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        showResultListView()
        mSearchAdapter?.setNewData(itemList)
        setLoadStatus(hasNextPage)
    }

    override fun showLoadMoreList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        mSearchAdapter?.addData(itemList)
        setLoadStatus(hasNextPage)
    }

    private fun setLoadStatus(hasNextPage: Boolean) {
        if (hasNextPage) {
            mSearchAdapter?.loadMoreComplete()
        } else {
            mSearchAdapter?.loadMoreEnd()
        }
        mSearchAdapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        mTextTypeface = null
        super.onDestroy()
    }
}