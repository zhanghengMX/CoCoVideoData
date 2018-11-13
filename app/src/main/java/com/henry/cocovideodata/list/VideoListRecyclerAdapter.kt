package com.henry.cocovideodata.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.RecyclerOnItemClickListener

/**
 * author：heng.zhang
 * date：2018/11/12
 * description：
 */
class VideoListRecyclerAdapter(private val datas : List<String>) : RecyclerView.Adapter<VideoListRecyclerAdapter.MyViewHolder>() {
    private var onclickListener : RecyclerOnItemClickListener<String>? = null

    fun setOnClickListener(listener : RecyclerOnItemClickListener<String>) {
        onclickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_video_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = datas[position]
        holder.videoNameTv.text = data
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