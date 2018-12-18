package cn.jacky.kotlindemo.test

import android.content.Context
import android.widget.Toast

/**
 * 作   者： by Hzj on 2018/1/4/004.
 * 描   述：
 * 修订记录：
 */
class Man(age: Int, name: String) : Person(age, name) {
    /**
     * 传参数时可以设置默认值，这样写的好处是不用写方法重载
     */
    fun toast(context: Context, message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    /**
     * 基本数据类型
     */
    fun testBasicDataType() {
        /**
         * var 可变变量，即普通变量
         * val 不可变变量，类似于java中final修饰的变量
         */

        val man = Man(25, "老李")
        var add = man.add(5_4, 56)

        man.age = 50

        println(man.name + "============================" + man.age)

        man.add(1, 2, 3, 4)

        println("============================")

        /**
         * 使用 until 函数排除结束元素
         *  包左不包右
         */
//    for (m in 1 until 6) print(m)

        println("============比较================")
        /**
         * ==比较值
         * ===比较地址
         */
        val a: Int = -129
        var result: Boolean
        println(a === a) // true，值相等，对象地址相等
        //经过了装箱，创建了两个不同的对象
        val boxedA: Int? = a
        result = a == boxedA
        println("a==boxedA:$result")
        result = a === boxedA
        /*
            上面定义的变量是Int类型，大于127其内存地址不同，反之则相同。
            这是`kotlin`的缓存策略导致的，而缓存的范围是` -128 ~ 127 `。
            故，下面的打印为false
        */
        println("a===boxedA:$result")

        val anotherBoxedA: Int? = a

        //类型显式转换
        val b = a.toLong()

        //虽然经过了装箱，但是值是相等的，都是10000
        println(boxedA == anotherBoxedA) // true，值相等
        println(boxedA === anotherBoxedA) //  false，值相等，对象地址不一样

        println("============Char,String================")
        val char: Char = 'a'

        /**
         * $变量名
         * ${表达式}
         */
        println("char:${char.toByte()}")
        println("char:${char.toInt()}")
        println("char:${char.toDouble()}")
        println("char:${char.toFloat()}")

        val str: String = "welcome to kotlin"
        for (c in str) {
            print(c)
            print("\t")
        }

        val text1 = """我是
        |常量字
        |符串字符串"""
        val text2 = """我是
                常量字
        |符串字符串""".trimMargin()
        println(text1)
        println(text2)
        println("text2[1]:${text2[1]}")

        println("============数组================")
        /**
         * 数组
         */
        var arrayA = Array(3, { i -> (i + 10) })
        var arrayB = arrayOf(1, 2, 3)
        //初始化一个空数组，如若不予数组赋值则arr3[0]、arr3[1]、arr3[2]..皆为null
        var arrayC = arrayOfNulls<Int>(5)
        for (i in arrayA) {
            println("arrayA:$i")
        }
        for (i in arrayB) {
            println("arrayB:$i")
        }

        //为空数组ArrayC赋值
        arrayC[0] = 8
        arrayC[2] = 555
        for (i in arrayC) {
            println("arrayC:$i")
        }

        println("=====================原始类型数组============================")

        //表示原始类型的数组，没有装箱开销，它们分别是：
        //ByteArray => 表示字节型数组
        //ShortArray => 表示短整型数组
        //IntArray => 表示整型数组
        //LongArray => 表示长整型数组
        //BooleanArray => 表示布尔型数组
        //CharArray => 表示字符型数组
        //FloatArray => 表示浮点型数组
        //DoubleArray => 表示双精度浮点型数组
        //PS: Kotlin中不支持字符串类型这种原始类型数组

        val intArray = intArrayOf(5, 6, 8, 11)
        for (i in intArray) {
            println(i)
        }
    }

    /**
     * 逻辑控制语句：if,for,while
     */
    fun testControlStatement() {
        var a = 7
        var b = if (a < 3) 3 else a
        println("b:$b")
        println("==================until,downto,..====================")
        //until,downto,..
        for (i in 0 until 8 step 2) {
            println("until:$i")
        }

        for (i in 8 downTo 0) {
            println("downTo:$i")
        }

        for (i in 0..8) {
            println("..:$i")
        }

        val array = arrayOf(1, 5, false, 6, '9', 55)
        for (i in array.indices) {
            println("array.indices:$i")
            println("array.indices[$i]:${array[i]}")
        }

        val iterator = array.iterator()
        while (iterator.hasNext()) {
            println("iterator.hasNext:${iterator.next()}")
        }

        for ((index, value) in array.withIndex()) {
            println("index:$index---value:$value")
        }
        println("==================when,类似于java的switch,但是更牛逼====================")

        when (a) {
            1, 23 -> {
                println("1")
            }
            in 1..5 -> {
                println("1")
            }
            3 -> println("3")
            //else 相当于 default
            else -> println("unknown")
        }
        //while循环
        loop@ while (a < 100) {
            a++
            if (a == 50) {
                break@loop
            }
            println(a)
        }
        println("final:" + (if (a in 9.rangeTo(99)) a else 0))


    }


    fun testCar() {
        val car = Car()
        val car1 = Car(5)
        val car2 = Car(4, "奥迪")

        println("car:$car")
        println("car1:$car1")
        println("car2:$car2")

        /**
         * Kotlin 可以对一个类的属性和方法进行扩展，且不需要继承或使用 Decorator 模式。
         * 扩展是一种静态行为，对被扩展的类代码本身不会造成任何影响。
         */
        //扩展函数
        fun Car.drive(name: String) {
            println("$name is running")
        }

        car2.drive(car2.name!!)
    }

    fun testLet() {

        /**
         * 空安全
         */
        var age = null
        //类型后加？表示可为空
        var grade: Int? = null
        //字段后加？不处理返回值为null
        var c: String = ""
//    println(c?.toInt())

        println("grage为空:$grade")

        var ages = age?.toInt() ?: 1
        println("ages:$ages")
//    val a = "s is $age"
//    println("a:" + "${a.replace("is","was")}")

        println("===================可空变量===================")

        //?: 这个操作符表示在判断一个可空类型时，会返回一个我们自己设定好的默认值.
        //!! 这个操作符表示在判断一个可空类型时，会显示的抛出空引用异常（NullPointException）.
        //as? 这个操作符表示为安全的类型转换.
        val s: String? = null
        val result = s ?: "ab".length?.plus(4)?.minus(1)
        println("可空变量：$result")

        println("===================let===================")
        //let操作符的作用：当时用符号?.验证的时候忽略掉null
        //let的用法：变量?.let{ ... }
        val array = arrayOf(1, null, 2, 3, 4, null, 5, null, 6, 7, 8)
        for (i in array) {
            i?.let { println("let:$it") }
        }
    }
}

/**
 * 与Java不同的地方:kotlin的main函数写在class外面
 * ，而Java是写在class里面
 */
fun main(args: Array<String>) {
    var man = Man(2, "test")
//    man.testBasicDataType()
//    man.testControlStatement()
//    man.testCar()
    man.testLet()
}