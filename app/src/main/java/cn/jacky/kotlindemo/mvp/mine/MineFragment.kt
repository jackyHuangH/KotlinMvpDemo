package cn.jacky.kotlindemo.mvp.mine

import android.os.Bundle
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @author:Hzj
 * @date  :2018/10/31/031
 * desc  ：每日精选
 * record：
 */
class MineFragment : BaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_mine

    override fun initWidget() {
        GlideApp.with(this)
                .load(R.drawable.img_avatar)
                .transform(BlurTransformation(10))
                .placeholder(R.drawable.default_avatar)
                .into(iv_avatar_bg)

        ibt_about.setOnClickListener {
            //关于
        }

        tv_view_homepage.setOnClickListener {
            //个人主页
        }

        tv_watch_history.setOnClickListener {
            //观看历史
        }
    }

    override fun lazyLoad() {

    }
}