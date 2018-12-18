package cn.jacky.kotlindemo.mvp.search

import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity

/**
 * @author:Hzj
 * @date  :2018/12/17/017
 * desc  ：
 * record：
 */
class SearchActivity : BaseActivity() {

    override fun getLayoutRes(): Int = R.layout.activity_search

    override fun initWidget() {
        setupEnterAnimation()
        setupExitAnimation()
    }

    /**
     * 退场动画
     */
    private fun setupExitAnimation() {
        val fade = Fade()
        fade.duration = 500
        window.returnTransition = fade
    }

    /**
     * 设置入场动画
     */
    private fun setupEnterAnimation() {
        val transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                //z展示揭露动画
                animateRevealShow()
            }
        })
    }

    /**
     * //z展示揭露动画
     */
    private fun animateRevealShow() {

    }

    companion object {

    }
}