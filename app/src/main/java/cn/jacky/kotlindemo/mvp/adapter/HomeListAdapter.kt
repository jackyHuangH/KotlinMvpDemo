package cn.jacky.kotlindemo.mvp.adapter

import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.util.durationFormat
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author:Hzj
 * @date  :2018/12/11/011
 * desc  ：首页列表adapter
 * record：
 */
class HomeListAdapter(data: List<HomeBean.Issue.Item>?) :
    BaseMultiItemQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(data) {
    companion object {
        /***item***/
        const val ITEM_TYPE_CONTENT = 1

        /***textHeader***/
        const val ITEM_TYPE_TEXT_HEADER = 2
    }

    /**
     * 构造函数中的初始化
     * 多布局代理模式实现，减小耦合
     */
    init {
        addItemType(ITEM_TYPE_CONTENT, R.layout.recycle_item_home_content)
        addItemType(ITEM_TYPE_TEXT_HEADER, R.layout.recycle_item_home_text)
    }

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue.Item?) {

        when (helper.itemViewType) {
            ITEM_TYPE_TEXT_HEADER -> {
                //text header
                helper.setText(R.id.tv_text_header, item?.data?.text ?: "")
            }
            ITEM_TYPE_CONTENT -> {
                //item
                val data = item?.data
                setVideoContent(helper, data)
            }
            else -> {
                throw IllegalAccessException("api解析错误，未知类型")
            }
        }
    }

    /**
     * 设置视频内容
     */
    private fun setVideoContent(helper: BaseViewHolder, itemData: HomeBean.Issue.Item.Data?) {
        val tagStart = "#"

        val cover = itemData?.cover?.feed
        var avator = itemData?.author?.icon
        //作者出处为空，就先获取提供者的信息
        if (avator.isNullOrEmpty()) {
            avator = itemData?.provider?.icon
        }
        //封面
        GlideApp
            .with(mContext)
            .load(cover)
            .placeholder(R.drawable.placeholder_banner)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(helper.getView(R.id.iv_cover_feed))
        //提供者信息
        // 头像
        GlideApp
            .with(mContext)
            .load(avator)
            .placeholder(R.drawable.default_avatar)
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(helper.getView(R.id.iv_avatar))
        helper.setText(R.id.tv_title, itemData?.title ?: "")
        //遍历标签
        var tagText: String? = tagStart
        itemData?.tags?.take(4)?.forEach {
            tagText += it.name + "/"
        }
        // 格式化时间
        var durationFormat = durationFormat(itemData?.duration)
        tagText += durationFormat
        helper.setText(R.id.tv_tag, tagText)
        helper.setText(R.id.tv_category, tagStart + itemData?.category)
    }
}