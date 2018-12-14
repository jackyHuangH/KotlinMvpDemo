package com.huangzj.kotlindemo.mvp.main

import android.app.Activity
import android.support.v4.app.Fragment
import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.mvp.baseview.BaseActivity
import com.huangzj.kotlindemo.mvp.discovery.DiscoveryFragment
import com.huangzj.kotlindemo.mvp.home.HomeFragment
import com.huangzj.kotlindemo.mvp.hot.HotFragment
import com.huangzj.kotlindemo.mvp.mine.MineFragment
import com.zenchn.support.managers.HFragmentManager
import com.zenchn.support.router.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var mTitles: Array<String>

    private lateinit var mFragments: Array<Fragment>

    private val mFragmentManagerHelper: HFragmentManager by lazy {
        HFragmentManager(supportFragmentManager, R.id.fl_container)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initWidget() {
        mTitles = arrayOf(getString(R.string.title_home),
                getString(R.string.title_discovery),
                getString(R.string.title_hot),
                getString(R.string.title_mine))

        mFragments = arrayOf(HomeFragment.getInstance(mTitles[0]),
                DiscoveryFragment.getInstance(mTitles[1]),
                HotFragment.getInstance(mTitles[2]),
                MineFragment.getInstance(mTitles[3]))

        //默认显示首页
        mFragmentManagerHelper.add(mFragments[0])
        bottom_layout.setOnItemSelectedListener { bottomBarItem, previousPosition, currentPosition ->
            mFragmentManagerHelper.switchFragment(mFragments[currentPosition])
        }
    }

    private var mExitTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
            finish()
        } else {
            mExitTime = System.currentTimeMillis()
            showMessage("再按一次退出程序")
        }
    }

    companion object {
        fun launch(from: Activity) {
            Router
                    .newInstance()
                    .from(from)
                    .to(MainActivity::class.java)
                    .launch()
        }
    }
}
