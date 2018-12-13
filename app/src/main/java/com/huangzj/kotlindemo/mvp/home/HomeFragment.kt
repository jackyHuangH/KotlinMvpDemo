package com.huangzj.kotlindemo.mvp.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.adapter.HomeListAdapter
import com.huangzj.kotlindemo.mvp.baseview.BaseFragment
import com.youth.banner.Banner
import com.zenchn.apilib.entity.HomeBean
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.find
import java.util.*

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：每日精选
 * record：
 */
class HomeFragment : BaseFragment(), HomeContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    private var mTitle: String? = null
    private var mHomeAdapter: HomeListAdapter? = null

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
        mPresenter.requestHomeData(num)
    }

    private fun initRecyclerView() {
        rlv.layoutManager = LinearLayoutManager(activity)

        val emptyList: List<HomeBean.Issue.Item> = Collections.emptyList<HomeBean.Issue.Item>()
        mHomeAdapter = activity?.let { HomeListAdapter(emptyList) }
        mHomeAdapter?.setOnLoadMoreListener(this, rlv)
        rlv.adapter = mHomeAdapter
        var header = LayoutInflater.from(activity).inflate(R.layout.recycle_header_home, null)
        initHeaderBanner(header)
        mHomeAdapter?.addHeaderView(header)
    }

    private fun initHeaderBanner(header: View) {
        var banner = header.find(R.id.banner_home) as Banner

    }

    override fun showBannerList(homeBean: HomeBean) {

    }

    private fun initRefreshLayout() {
        swipe_refresh.setColorSchemeResources(R.color.color_0288d1, R.color.color_02c1e1)
        swipe_refresh.setOnRefreshListener {
            //刷新数据

        }
    }

    override fun onLoadMoreRequested() {
        mPresenter.loadMoreData()
    }

    override fun setHomeData(homeBean: HomeBean, hasNextPage: Boolean) {
        if (swipe_refresh.isRefreshing) {
            swipe_refresh.isRefreshing = false
        }
        mHomeAdapter?.setNewData(homeBean.issueList[0].itemList)
        setLoadStatus(hasNextPage)
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>, hasNextPage: Boolean) {
        mHomeAdapter?.addData(itemList)
        setLoadStatus(hasNextPage)
    }

    private fun setLoadStatus(hasNextPage: Boolean) {
        if (hasNextPage) {
            mHomeAdapter?.loadMoreComplete()
        } else {
            mHomeAdapter?.loadMoreEnd()
        }
        mHomeAdapter?.notifyDataSetChanged()
    }
}