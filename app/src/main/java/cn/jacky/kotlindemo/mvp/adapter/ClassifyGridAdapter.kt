package cn.jacky.kotlindemo.mvp.adapter

import android.view.ViewGroup
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zenchn.apilib.entity.CategoryBean
import com.zenchn.support.kit.AndroidKit

/**
 * @author:Hzj
 * @date  :2018/12/24/024
 * desc  ：分类主页grid adapter
 * record：
 */
class ClassifyGridAdapter(layoutRes: Int, data: List<CategoryBean>) :
        BaseQuickAdapter<CategoryBean, BaseViewHolder>(layoutRes, data) {

    override fun convert(helper: BaseViewHolder, item: CategoryBean?) {
        GlideApp
                .with(mContext)
                .load(item?.bgPicture)
                .placeholder(R.drawable.placeholder_banner)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(helper.getView(R.id.iv_classify))

        helper.setText(R.id.tv_classify_name, "#${item?.name}")

        //重新计算item宽高
        val screenWidth = AndroidKit.Dimens.getScreenWidth()
        val defaultPadding = AndroidKit.Dimens.dp2px(3)
        val newSize = (screenWidth - defaultPadding) / 2
        helper.itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, newSize)
    }

}