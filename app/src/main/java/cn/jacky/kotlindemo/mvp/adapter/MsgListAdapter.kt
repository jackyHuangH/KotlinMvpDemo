package cn.jacky.kotlindemo.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.jacky.kotlindemo.R
import org.jetbrains.anko.find

/**
 * 作   者： by Hzj on 2018/1/5/005.
 * 描   述：
 * 修订记录：
 */
class MsgListAdapter(val datas: List<String>) : RecyclerView.Adapter<MsgListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = datas[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycle_item_home_content, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.find(R.id.tv_title) as TextView
    }
}