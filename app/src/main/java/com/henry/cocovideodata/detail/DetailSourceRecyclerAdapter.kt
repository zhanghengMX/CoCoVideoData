package com.henry.cocovideodata.top250

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.henry.cocovideodata.R
import com.henry.cocovideodata.bean.VideoUrl
import com.henry.cocovideodata.jsoup.PlaySource

/**
 * author：heng.zhang
 * date：2018/11/12
 * description：
 */
class DetailSourceRecyclerAdapter(private val datas : MutableList<VideoUrl>) : RecyclerView.Adapter<DetailSourceRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_detail_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    fun addVideoSourceItem(list : MutableList<VideoUrl>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = datas[position]
        holder.videoIndexTv.text = data.index.toString()
        for (url in data.urls) {
            if (url.endsWith("m3u8")) {
                holder.m3u8SourceTv.text = url
            }else{
                holder.webSourceTv.text = url
            }
        }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val videoIndexTv = itemView?.findViewById(R.id.videoIndexTv) as TextView
        val webSourceTv = itemView?.findViewById(R.id.webSourceTv) as TextView
        val m3u8SourceTv = itemView?.findViewById(R.id.m3u8SourceTv) as TextView
    }
}