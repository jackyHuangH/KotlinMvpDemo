package cn.jacky.kotlindemo.mvp.adapter

import android.app.Activity
import android.widget.ImageView
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.mvp.videodetail.VideoDetailActivity
import cn.jacky.kotlindemo.util.durationFormat
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zenchn.apilib.entity.HomeBean
import java.util.*

/**
 * @author:Hzj
 * @date  :2018/12/24/024
 * desc  ：关注item里的水平列表Adapter
 * record：
 */
class AttentionHorizontalAdapter(layoutRes: Int, data: ArrayList<HomeBean.Issue.Item>) :
        BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(layoutRes, data) {

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue.Item?) {
        val horizontalItemData = item?.data

        GlideApp
                .with(mContext)
                .load(horizontalItemData?.cover?.feed)
                .placeholder(R.drawable.placeholder_banner)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(helper.getView(R.id.iv_cover_feed))

        helper.setText(R.id.tv_title, horizontalItemData?.title ?: "")

        // 格式化时间
        val timeFormat = durationFormat(horizontalItemData?.duration)
        if (horizontalItemData?.tags != null && horizontalItemData.tags.size > 0) {
            helper.setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
        } else {
            helper.setText(R.id.tv_tag, "#$timeFormat")
        }

        helper.itemView.setOnClickListener { view ->
            // 跳转视频详情
            val ivCover = helper.getView<ImageView>(R.id.iv_cover_feed)
            item?.let { VideoDetailActivity.launch(mContext as Activity, ivCover, it) }
        }
    }
}