package com.henry.cocovideodata.top250

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.RecyclerOnItemClickListener

/**
 * author：heng.zhang
 * date：2018/11/12
 * description：
 */
class MainPageRecyclerAdapter(val datas : MutableList<String>) : RecyclerView.Adapter<MainPageRecyclerAdapter.MyViewHolder>() {
    private var onclickListener : RecyclerOnItemClickListener<String>? = null

    fun setOnClickListener(listener : RecyclerOnItemClickListener<String>) {
        onclickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_main_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mainPageItemTv.text = datas[position]
        holder.itemView.setOnClickListener { onclickListener?.onClick(datas[position]) }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val mainPageItemTv = itemView?.findViewById(R.id.mainPageItemTv) as TextView
    }
}