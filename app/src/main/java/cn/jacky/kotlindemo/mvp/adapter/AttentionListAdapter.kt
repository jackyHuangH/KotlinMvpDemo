package cn.jacky.kotlindemo.mvp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：关注列表
 * record：
 */
class AttentionListAdapter(layoutRes: Int, data: ArrayList<HomeBean.Issue.Item>) : BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(layoutRes, data) {

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {

    }
}