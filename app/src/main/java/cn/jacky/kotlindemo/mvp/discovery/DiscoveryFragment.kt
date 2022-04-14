package cn.jacky.kotlindemo.mvp.discovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.adapter.BaseFragmentPagerAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment
import cn.jacky.kotlindemo.mvp.discovery.attention.AttentionFragment
import cn.jacky.kotlindemo.mvp.discovery.classify.ClassifyFragment
import cn.jacky.kotlindemo.util.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_discovery_hot.*

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：发现
 * record：
 */
class DiscoveryFragment : BaseFragment() {
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_discovery_hot

    override fun initWidget() {
        tv_toorbar_title.text = mTitle
        initTabLayout()
    }

    private fun initTabLayout() {
        val titles = ArrayList<String>()
        val fragments = ArrayList<Fragment>()
        titles.add(getString(R.string.discover_tab_title_attention))
        titles.add(getString(R.string.discover_tab_title_classify))
        fragments.add(AttentionFragment.getInstance())
        fragments.add(ClassifyFragment.getInstance())
        val fragmentPagerAdapter = BaseFragmentPagerAdapter(childFragmentManager, fragments, titles)
        vp.adapter = fragmentPagerAdapter
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setupWithViewPager(vp)
        TabLayoutHelper.setUpIndicatorWidth(tabLayout)
    }

    override fun lazyLoad() {
    }
}