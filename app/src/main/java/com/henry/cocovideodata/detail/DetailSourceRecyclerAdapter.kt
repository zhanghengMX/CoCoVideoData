package com.henry.cocovideodata.list

import android.app.Application
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.henry.cocovideodata.App
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.RecyclerOnItemClickListener
import com.henry.cocovideodata.bean.VideoListItem

/**
 * author：heng.zhang
 * date：2018/11/12
 * description：
 */
class DetailSourceRecyclerAdapter(private val datas : MutableList<VideoListItem>) : RecyclerView.Adapter<DetailSourceRecyclerAdapter.MyViewHolder>() {
    private var onclickListener : RecyclerOnItemClickListener<VideoListItem>? = null

    //TODO 复制列表页的，还未修改
    fun setOnClickListener(listener : RecyclerOnItemClickListener<VideoListItem>) {
        onclickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_video_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    fun addVideoListItem(list : MutableList<VideoListItem>) {
        datas.addAll(list)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = datas[position]
        holder.videoNameTv.text = data.name
        holder.actorTv.text = data.casts.toString()
        holder.directorTv.text = data.directors.toString()
        holder.scoreTv.text = data.average
        Glide.with(holder.itemView.context)
                .load(data.images[1])
                .into(holder.posterIv)
        holder.itemView.setOnClickListener { onclickListener?.onClick(data) }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val videoNameTv = itemView?.findViewById(R.id.tv_video_name) as TextView
        val actorTv = itemView?.findViewById(R.id.tv_actor) as TextView
        val directorTv = itemView?.findViewById(R.id.tv_director) as TextView
        val scoreTv = itemView?.findViewById(R.id.tv_score) as TextView
        val posterIv = itemView?.findViewById(R.id.iv_poster) as ImageView
    }
}