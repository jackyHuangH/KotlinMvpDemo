package cn.jacky.kotlindemo.mvp.discovery.classify

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.adapter.ClassifyGridAdapter
import cn.jacky.kotlindemo.mvp.baseview.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zenchn.apilib.entity.CategoryBean
import com.zenchn.support.widget.itemdecoration.GridSpaceItemDecoration
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
        rlv.addItemDecoration(GridSpaceItemDecoration(2, categoryList.size, 10))
        val classifyGridAdapter = ClassifyGridAdapter(R.layout.recycle_item_grid_classify, categoryList)
        classifyGridAdapter.onItemClickListener = this
        rlv.adapter = classifyGridAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        //todo 跳转分类详情
    }

}