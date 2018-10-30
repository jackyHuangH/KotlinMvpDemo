package com.huangzj.kotlindemo.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Switch
import android.widget.TextView
import com.huangzj.kotlindemo.R
import com.huangzj.kotlindemo.adapter.MsgListAdapter
import com.huangzj.kotlindemo.entity.Man
import com.huangzj.kotlindemo.entity.Person
import com.huangzj.kotlindemo.ui.MainActivity.Constans.NUM_B
import org.jetbrains.anko.find
import java.util.*
import kotlin.collections.ArrayList

/**
 *
顶层声明常量
 */
private const val NUM_A: String = "顶层声明常量"

class MainActivity : AppCompatActivity() {

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
    object Constans {
        const val NUM_B = "object修饰的类中声明常量"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRlv = find(R.id.list)
        mRlv.layoutManager = LinearLayoutManager(this)
        val datas = ArrayList<String>()
        for (i in 0 until 25) {
            datas.add("元素$i")
        }
        val adapter = MsgListAdapter(datas)
        mRlv.adapter = adapter

        val tv = findViewById<TextView>(R.id.tv)

        val swbtn = find(R.id.sw_btn) as Switch
        swbtn.setOnCheckedChangeListener { _, isChecked ->
            tv.text = if (isChecked) "打开" else "关闭"
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
        tv.text = String.format(Locale.CHINA, "$eat:$NUM_A=$NUM_B-$NUM_C")

        val zhang = Person(5)
        println("zhang:$zhang")
        val wang = Person(52, "老王")
        println("zhang:$wang")

    }

    /**
     * 伴生类
     */
    companion object {
        const val NUM_C = "伴生对象中声明"
    }
}
