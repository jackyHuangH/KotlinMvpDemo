package cn.jacky.kotlindemo.mvp.discovery.classify

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.adapter.ClassifyGridAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment
import cn.jacky.kotlindemo.mvp.classifydetail.ClassifyDetailActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import cn.jacky.kotlindemo.api.bean.CategoryBean
import com.zenchn.support.widget.itemdecoration.GridDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_classify.*

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：分类
 * record：
 */
class ClassifyFragment : BaseFragment(), ClassifyContract.View, BaseQuickAdapter.OnItemClickListener {

    private val mPresenter by lazy { ClassifyPresenterImpl(this) }

    companion object {
        fun getInstance(): ClassifyFragment {
            val fragment = ClassifyFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun lazyLoad() {
        mPresenter.getClassifyList()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_classify

    override fun initWidget() {
        rlv.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun showClassifyList(categoryList: List<CategoryBean>) {
        rlv.addItemDecoration(GridDividerItemDecoration(activity))
        val classifyGridAdapter = ClassifyGridAdapter(R.layout.recycle_item_grid_classify, categoryList)
        classifyGridAdapter.onItemClickListener = this
        rlv.adapter = classifyGridAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        // 跳转分类详情
        val categoryBean = adapter.getItem(position) as CategoryBean
        activity?.let { ClassifyDetailActivity.launch(it, categoryBean) }
    }

}