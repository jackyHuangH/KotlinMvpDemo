package cn.jacky.kotlindemo.mvp.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Gravity
import cn.jacky.kotlindemo.R
import cn.jacky.kotlindemo.api.bean.HomeBean
import cn.jacky.kotlindemo.wrapper.glide.GlideApp
import cn.jacky.kotlindemo.wrapper.snaphelper.GravitySnapHelper
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author:Hzj
 * @date  :2018/12/21/021
 * desc  ：关注列表
 * record：
 */
class AttentionListAdapter(layoutRes: Int, data: ArrayList<HomeBean.Issue.Item>) : BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(layoutRes, data) {

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue.Item?) {
        val header = item?.data?.header

        GlideApp
                .with(mContext)
                .load(header?.icon)
                .placeholder(R.drawable.default_avatar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(helper.getView(R.id.iv_avatar))

        helper.setText(R.id.tv_author_name, header?.title)
        helper.setText(R.id.tv_author_desc, header?.description)
        helper.addOnClickListener(R.id.tv_follow)

        val rlvAttentionWorks = helper.getView<RecyclerView>(R.id.rlv_attention_works)
        //设置水平滑动recyclerView
        rlvAttentionWorks.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        GravitySnapHelper(Gravity.START).attachToRecyclerView(rlvAttentionWorks)
        val horizontalAdapter = AttentionHorizontalAdapter(R.layout.recycle_item_attention_horizontal, item?.data?.itemList!!)
        rlvAttentionWorks.adapter = horizontalAdapter
    }
}