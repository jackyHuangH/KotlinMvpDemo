package cn.jacky.kotlindemo

import org.junit.Test

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：
 * record：
 */
class KotlinTest {
    @Test
    fun textForeach() {
        (0..20).forEach continuing@{
            if (it == 10) {
                //return@函数名，相当于break
                //return@continuing ，相当于continue
                return@textForeach
            }
            println(it)
        }
    }
}