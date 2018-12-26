package cn.jacky.kotlindemo.mvp.adapter

import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.util.durationFormat
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zenchn.apilib.entity.HomeBean

/**
 * @author:Hzj
 * @date  :2018/12/26/026
 * desc  ：
 * record：
 */
class ClassifyDetailListAdapter(layoutRes: Int, data: List<HomeBean.Issue.Item>) :
        BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(layoutRes, data) {
    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue.Item?) {
        val itemData = item?.data
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