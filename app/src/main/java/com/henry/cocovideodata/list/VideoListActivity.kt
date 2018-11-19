package com.henry.cocovideodata.list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.henry.cocovideodata.App
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.RecyclerOnItemClickListener
import com.henry.cocovideodata.bean.VideoListItem
import kotlinx.android.synthetic.main.activity_video_list.*

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class VideoListActivity : AppCompatActivity(), VideoListContract.View {
    override fun refreshData(data : Any) {
        val list = data as MutableList<VideoListItem>
        runOnUiThread { recyclerAdapter.addVideoListItem(list) }
    }

    override var presenter: VideoListContract.Presenter = VideoListPresenter(this)
    private lateinit var recyclerAdapter: VideoListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)
        initView()
        presenter.start(Bundle())
    }
    private fun initView() {
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = VideoListRecyclerAdapter(mutableListOf())
        recyclerAdapter.setOnClickListener(object : RecyclerOnItemClickListener<VideoListItem> {
            override fun onClick(t: VideoListItem) {
                //TODO 打开影片详情页
            }
        })
        recyclerView.adapter = recyclerAdapter
    }
}