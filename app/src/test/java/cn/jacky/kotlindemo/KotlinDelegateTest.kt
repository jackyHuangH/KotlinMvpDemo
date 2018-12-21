package cn.jacky.kotlindemo

import kotlin.reflect.KProperty

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：
 * record：
 */

//------------------------kotlin 属性委托-------------------------------

//定义属性被委托的类
class KotlinDelegateTest {
    var v: String by Delegate()

    //定义委托类
    class Delegate {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef,这里委托了${property.name}属性"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$thisRef 的 ${property.name}属性 被赋值为$value")
        }
    }
}

fun main(args: Array<String>) {
    val man = KotlinDelegateTest()
    //这里访问该属性，调用getValue
    println("$man.v")

    //这里调用setValue
    man.v = "超级学校霸王"
    println("赋值后：${man.v}")
}