package cn.jacky.kotlindemo.mvp.main

import android.app.Activity
import androidx.fragment.app.Fragment
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.mvp.discovery.DiscoveryFragment
import cn.jacky.kotlindemo.mvp.home.HomeFragment
import cn.jacky.kotlindemo.mvp.hot.HotFragment
import cn.jacky.kotlindemo.mvp.mine.MineFragment
import com.zenchn.support.managers.FragmentSwitchHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    private lateinit var mTitles: Array<String>

    private lateinit var mFragments: Array<Fragment>

    private val mFragmentSwitchHelper: FragmentSwitchHelper by lazy {
        FragmentSwitchHelper(supportFragmentManager, R.id.fl_container)
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
        mFragmentSwitchHelper.add(mFragments[0])
        bottom_layout.setOnItemSelectedListener { bottomBarItem, previousPosition, currentPosition ->
            mFragmentSwitchHelper.switchFragment(mFragments[currentPosition])
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
            from.startActivity<MainActivity>()

            //强大的Anko给我们提供了一种极为方便的写法。
            // 无论是无参还是有参，还是需要RequestCode，都非常简洁易懂，是不是一看就特别心动？
            //startActivity<MainActivity>()
            //startActivity<MainActivity>("userid" to 10001, "username" to "ricky")
            //startActivityForResult<MainActivity>(101, "userid" to 10001, "username" to "ricky")
        }
    }
}
