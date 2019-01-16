package cn.jacky.kotlindemo.mvp.hot

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.api.bean.TabInfoBean
import cn.jacky.kotlindemo.mvp.adapter.BaseFragmentPagerAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment
import cn.jacky.kotlindemo.mvp.hot.rank.RankFragment
import kotlinx.android.synthetic.main.fragment_discovery_hot.*

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：热门
 * record：
 */
class HotFragment : BaseFragment(), HotContract.View {

    private val mPresenter by lazy { HotPrensenterImpl(this) }
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_discovery_hot
    }

    override fun initWidget() {
        tv_toorbar_title.text = mTitle
        mPresenter.getTabInfo()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        val titles = ArrayList<String>()
        val fragments = ArrayList<Fragment>()

        val tabList = tabInfoBean.tabInfo.tabList
        //mapTo 高阶函数使用，类似rxjava #map
        tabList.mapTo(titles) {
            it.name
        }
        tabList.mapTo(fragments) {
            RankFragment.getInstance(it.apiUrl)
        }

        val fragmentPagerAdapter = BaseFragmentPagerAdapter(childFragmentManager, fragments, titles)
        vp.adapter = fragmentPagerAdapter
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setupWithViewPager(vp)
    }

    override fun lazyLoad() {
    }

    override fun onDestroyView() {
        mPresenter.onDestroy()
        super.onDestroyView()
    }
}