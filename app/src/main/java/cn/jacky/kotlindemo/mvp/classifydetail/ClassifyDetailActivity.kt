package cn.jacky.kotlindemo.mvp.classifydetail

import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.baseview.BaseActivity
import com.zenchn.apilib.entity.CategoryBean

/**
 * @author:Hzj
 * @date  :2018/12/25/025
 * desc  ：分类详情
 * record：
 */
class ClassifyDetailActivity : BaseActivity(), ClassifyDetailContract.View {

    override fun getLayoutRes(): Int = R.layout.activity_classify_detail

    override fun initWidget() {
    }

    override fun showClassifyList(categoryList: List<CategoryBean>) {

    }
}