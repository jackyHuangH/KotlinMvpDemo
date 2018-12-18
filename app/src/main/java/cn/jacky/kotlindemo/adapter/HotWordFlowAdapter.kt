package cn.jacky.kotlindemo.adapter

import android.support.annotation.LayoutRes
import cn.jacky.kotlindemo.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author:Hzj
 * @date  :2018/12/18
 * desc  ：热搜词瀑布流 adapter
 * record：
 */
class HotWordFlowAdapter(@LayoutRes layoutRes: Int, data: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutRes, data) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_tag_name, item)
    }
}