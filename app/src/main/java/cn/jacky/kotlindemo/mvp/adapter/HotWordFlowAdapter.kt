package cn.jacky.kotlindemo.mvp.adapter

import android.support.annotation.LayoutRes
import android.widget.TextView
import cn.jacky.kotlindemo.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * @author:Hzj
 * @date  :2018/12/18
 * desc  ：热搜词瀑布流 adapter
 * record：
 */
class HotWordFlowAdapter(@LayoutRes layoutRes: Int, data: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutRes, data) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        //设置flexgrow为1，自动填充满
        val params = helper.getView<TextView>(R.id.tv_tag_name).layoutParams
        if (params is FlexboxLayoutManager.LayoutParams) {
            params.flexGrow = 1.0f
        }
        helper.setText(R.id.tv_tag_name, item)
    }
}