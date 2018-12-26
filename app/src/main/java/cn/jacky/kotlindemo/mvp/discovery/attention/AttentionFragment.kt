package cn.jacky.kotlindemo.mvp.discovery.attention

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.adapter.AttentionListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment
import cn.jacky.kotlindemo.mvp.videodetail.VideoDetailActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zenchn.apilib.entity.HomeBean
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：关注
 * record：
 */
class AttentionFragment : BaseFragment(), AttentionContract.View, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private val mPresenter by lazy { AttentionPresenterImpl(this) }

    private lateinit var mListAdapter: AttentionListAdapter

    companion object {
        fun getInstance(): AttentionFragment {
            val fragment = AttentionFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }

    override fun getLayoutRes(): Int = R.layout.layout_recycler_view

    override fun initWidget() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val emptyList: ArrayList<HomeBean.Issue.Item> = ArrayList()
        mListAdapter = AttentionListAdapter(R.layout.recycle_item_attention, emptyList)
        mListAdapter.setOnLoadMoreListener(this, recyclerView)
        mListAdapter.onItemClickListener = this
        recyclerView.adapter = mListAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        // 跳转视频详情
        val item = adapter.getItem(position) as HomeBean.Issue.Item
        activity?.let { VideoDetailActivity.launch(it, view!!, item) }
    }

    override fun onLoadMoreRequested() {
        //加载更多
        mPresenter.loadMoreData()
    }

    override fun setNewFollowList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        mListAdapter.setNewData(itemList)
        setLoadStatus(hasNextPage)
    }

    override fun setLoadMoreFollowList(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        mListAdapter.addData(itemList)
        setLoadStatus(hasNextPage)
    }

    private fun setLoadStatus(hasNextPage: Boolean) {
        if (hasNextPage) {
            mListAdapter.loadMoreComplete()
        } else {
            mListAdapter.loadMoreEnd()
        }
        mListAdapter.notifyDataSetChanged()
    }
}