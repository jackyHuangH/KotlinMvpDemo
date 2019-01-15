package cn.jacky.kotlindemo.mvp.adapter

import android.support.annotation.LayoutRes
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.util.durationFormat
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import cn.jacky.kotlindemo.api.bean.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/18/018
 * desc  ：搜索列表adapter
 * record：
 */
class SearchListAdapter(@LayoutRes layoutResId: Int, data: List<HomeBean.Issue.Item>?) :
        BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue.Item) {
        val itemData = item.data
        val cover = itemData?.cover?.feed ?: ""
        // 加载封页图
        GlideApp.with(mContext)
                .load(cover)
                .apply(RequestOptions().placeholder(R.drawable.placeholder_banner))
                .transition(DrawableTransitionOptions().crossFade())
                .into(helper.getView(R.id.iv_image))
        helper.setText(R.id.tv_title, itemData?.title ?: "")

        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)
        helper.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")
    }
}