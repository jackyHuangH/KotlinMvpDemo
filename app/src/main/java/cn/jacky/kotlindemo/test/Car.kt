package cn.jacky.kotlindemo.test

/**
 * @author:Hzj
 * @date :2018/10/24/024
 * desc  ：没有主构造函数
 * record：
 */
class Car {
    private var price: Int = 0

    var name: String? = null

    //这三个都是次构造函数
    constructor() {}

    constructor(price: Int) {
        this.price = price
    }

    constructor(price: Int, name: String) {
        this.price = price
        this.name = name
    }

    override fun toString(): String {
        return "name:${this.name},price:${this.price}"
    }
}
