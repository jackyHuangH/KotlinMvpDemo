package cn.jacky.kotlindemo.test

/**
 * 作   者： by Hzj on 2018/1/4/004.
 * 描   述：Koltin 中的类可以有一个 主构造器，以及一个或多个次构造器，主构造器是类头部的一部分，位于类名称之后:
class Person constructor(firstName: String) {
}
如果主构造器没有任何注解，也没有任何可见度修饰符，那么constructor关键字可以省略。

class Person(firstName: String) {
}
 * 修订记录：
 */
open class Person constructor(age: Int) {

    //构造里的初始化方法
    init {
        println("初始化:age=$age")
    }

    //次构造函数
    constructor (age: Int, name: String) : this(age) {
        this.name = name
        println("初始化:name=$name")
    }


    var name: String? = "z"
        get() = "姓名:$field"
        set(value) {
            //如果可空类型变量为null时，返回null
            field = value?.toUpperCase()
        }

    var age: Int = age
        get() = field
        set(value) {
            field = if (value > 30) 30 else value
        }


    //kotlin函数写法
    fun eat(food: String, water: String): String {
        return food + water
    }

    fun add(x: Int, y: Int) = x + y

    /**
     * 可变参数写法，用vararg 修饰参数
     */
    fun add(vararg v: Int) {
        var temp: Int = 0
        for (vt in v) {
            temp += vt
            println(temp)
        }
    }
}