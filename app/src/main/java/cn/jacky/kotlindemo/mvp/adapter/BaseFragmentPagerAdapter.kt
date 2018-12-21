package cn.jacky.kotlindemo.mvp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：viewpager+fragment切换adapter
 * record：该类内的每一个生成的 Fragment 都将保存在内存之中，
 * 因此适用于那些相对静态的页，数量也比较少的那种；
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 * 应该使用FragmentStatePagerAdapter。
 */
class BaseFragmentPagerAdapter(fm: FragmentManager, fragments: List<Fragment>, titles: List<String>) : FragmentPagerAdapter(fm) {

    private var mFragments: List<Fragment>? = fragments
    private var mTitles: List<String>? = titles

    override fun getPageTitle(position: Int): CharSequence? {
        return if (mTitles != null) {
            mTitles!![position]
        } else {
            ""
        }
    }

    override fun getItem(position: Int): Fragment {
        return mFragments!![position]
    }

    override fun getCount(): Int {
        return mFragments!!.size
    }
}