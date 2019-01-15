package cn.jacky.kotlindemo.mvp.hot.rank

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.adapter.ClassifyDetailListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment
import cn.jacky.kotlindemo.mvp.videodetail.VideoDetailActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import cn.jacky.kotlindemo.api.bean.HomeBean
import com.zenchn.support.widget.itemdecoration.VerticalItemDecoration
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * @author:Hzj
 * @date  :2018/12/26/026
 * desc  ：排行榜
 * record：
 */
class RankFragment : BaseFragment(), RankContract.View, BaseQuickAdapter.OnItemClickListener {

    private var mApiUrl: String? = null
    private lateinit var mListAdapter: ClassifyDetailListAdapter
    private val mPresenter by lazy { RankPrensenterImpl(this) }

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mApiUrl = apiUrl
            return fragment
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_rank

    override fun initWidget() {
        rlv_rank.layoutManager = LinearLayoutManager(activity)
        rlv_rank.addItemDecoration(VerticalItemDecoration(activity, 1, false))
    }

    override fun lazyLoad() {
        mApiUrl?.let { mPresenter.requestRankList(it) }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        // 跳转视频详情
        val item = adapter.getItem(position) as HomeBean.Issue.Item
        activity?.let { VideoDetailActivity.launch(it, view!!, item) }
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        mListAdapter = ClassifyDetailListAdapter(R.layout.recycle_item_classify_detail, itemList)
        mListAdapter.onItemClickListener = this
        val footer = LayoutInflater.from(activity).inflate(R.layout.recycle_footer_no_more, rlv_rank, false).apply {
            //do something 可以做初始化
        }
        mListAdapter.addFooterView(footer)
        rlv_rank.adapter = mListAdapter
    }
}