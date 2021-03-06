package cn.jacky.kotlindemo.test

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.adapter.MsgListAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import cn.jacky.kotlindemo.mvp.main.MainActivity
import cn.jacky.kotlindemo.test.TestActivity.Constants.NUM_B
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.find
import java.util.*
import kotlin.collections.ArrayList

/**
 *
顶层声明常量
 */
private const val NUM_A: String = "顶层声明常量"

class TestActivity : BaseActivity() {

    /**
     * 后期初始化属性
     */
    private lateinit var mRlv: RecyclerView

    /**
     * 声明一个延迟初始化（懒加载）属性
     */
    private val lazyString: String by lazy { "我是懒加载的内容" }

    /**
     * object对象声明，它相当于Java中一种形式的单例类
     */
    object Constants {
        const val NUM_B = "object修饰的类中声明常量"
    }

    override fun getLayoutRes(): Int = R.layout.activity_test

    override fun initWidget() {
        mRlv = find(R.id.list)
        mRlv.layoutManager = LinearLayoutManager(this)
        val datas = ArrayList<String>()
        for (i in 0 until 25) {
            datas.add("元素$i")
        }
        val adapter = MsgListAdapter(datas)
        mRlv.adapter = adapter

        sw_btn.setOnCheckedChangeListener { _, isChecked ->
            bt.text = if (isChecked) "打开" else "关闭"
        }

        bt.setOnClickListener {

        }

        bt_main.setOnClickListener {
            MainActivity.launch(this)
        }

        //对象实例化
        /**
         * 我是块注释,块注释允许嵌套
         * /**
         * 我也是块注释
         * */
         */
        val man = Man(25, "张三")
        val eat = man.eat("面包", lazyString)
        man.toast(this, eat)
        bt.text = String.format(Locale.CHINA, "$eat:$NUM_A=$NUM_B-$NUM_C")

        val zhang = Person(5)
        println("zhang:$zhang")
        val wang = Person(52, "老王")
        println("zhang:$wang")
    }

    /**
     * 伴生类:静态单例内部类
     */
    companion object Num {
        const val NUM_C = "伴生对象中声明"
    }
}
