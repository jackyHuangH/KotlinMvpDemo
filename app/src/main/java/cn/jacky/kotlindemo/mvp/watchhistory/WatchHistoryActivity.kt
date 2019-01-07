package cn.jacky.kotlindemo.mvp.watchhistory

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.R.id.rlv_history
import cn.jacky.kotlindemo.mvp.adapter.SearchListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.mvp.videodetail.VideoDetailActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.zenchn.apilib.entity.HomeBean
import com.zenchn.support.router.Router
import kotlinx.android.synthetic.main.activity_watch_history.*

/**
 * @author:Hzj
 * @date  :2019/1/2/002
 * desc  ：观看历史
 * record：
 */
class WatchHistoryActivity : BaseActivity(), WatchHistoryContract.View, BaseQuickAdapter.OnItemClickListener {

    private lateinit var mHistoryAdapter: SearchListAdapter

    private val mPresenter by lazy { WatchHistoryPresenterImpl(this) }

    override fun getLayoutRes(): Int = R.layout.activity_watch_history

    override fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_title_bg)
                .statusBarDarkFont(true, 0.2f)
        mImmersionBar.init()
    }

    override fun onResume() {
        super.onResume()
        //加载本地观看记录
        mPresenter.getWatchHistory()
    }

    override fun initWidget() {
        toolbar.setNavigationOnClickListener { onBackPressed() }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rlv_history.layoutManager = LinearLayoutManager(this)
        val emptyList: ArrayList<HomeBean.Issue.Item> = ArrayList()
        mHistoryAdapter = SearchListAdapter(R.layout.recycle_item_search, emptyList)
        mHistoryAdapter.onItemClickListener = this
        rlv_history.adapter = mHistoryAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        //跳转视频详情
        val item = adapter.getItem(position) as HomeBean.Issue.Item
        VideoDetailActivity.launch(this, view!!, item)
    }

    override fun setHomeNewData(itemList: ArrayList<HomeBean.Issue.Item>) {
        mHistoryAdapter.setNewData(itemList)
        rlv_history.smoothScrollToPosition(0)
    }

    companion object {
        fun launch(from: Activity) {
            Router
                    .newInstance()
                    .from(from)
                    .to(WatchHistoryActivity::class.java)
                    .launch()
        }
    }
}