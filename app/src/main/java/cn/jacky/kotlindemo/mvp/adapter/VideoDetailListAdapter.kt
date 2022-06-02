package cn.jacky.kotlindemo.mvp.adapter

import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.mvp.adapter.HomeListAdapter.Companion.ITEM_TYPE_TEXT_HEADER
import cn.jacky.kotlindemo.util.durationFormat
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author:Hzj
 * @date  :2018/12/19/019
 * desc  ：视频详情下方列表adapter
 * record：
 */
class VideoDetailListAdapter(data: List<HomeBean.Issue.Item>) :
    BaseMultiItemQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(data) {

    companion object {
        /***item***/
        const val ITEM_TYPE_CONTENT = 1

        /***textHeader***/
        const val ITEM_TYPE_TEXT_SECTION = 2
    }

    /**
     * 构造函数中的初始化
     * 多布局代理模式实现，减小耦合
     */
    init {
        addItemType(ITEM_TYPE_CONTENT, R.layout.recycle_item_video_detail_content)
        addItemType(ITEM_TYPE_TEXT_SECTION, R.layout.recycle_item_video_detail_section)
    }

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue.Item?) {
        when (helper.itemViewType) {
            ITEM_TYPE_TEXT_HEADER -> {
                //text header
                helper.setText(R.id.tv_section_name, item?.data?.text ?: "")
            }
            ITEM_TYPE_CONTENT -> {
                //item
                with(helper) {
                    setText(R.id.tv_title, item?.data?.title)
                    setText(R.id.tv_tag, "#${item?.data?.category} / ${durationFormat(item?.data?.duration)}")
                    //列表封面
                    GlideApp
                        .with(mContext)
                        .load(item?.data?.cover?.detail)
                        .placeholder(R.drawable.placeholder_banner)
                        .transform(RoundedCorners(5))
                        .into(getView(R.id.iv_video_small_card))
                }
            }
            else -> {
                throw IllegalAccessException("api解析错误，未知类型")
            }
        }
    }
}