package com.henry.cocovideodata.list

import android.content.Context
import android.content.Intent
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
import com.henry.cocovideodata.detail.VideoDetailActivity
import kotlinx.android.synthetic.main.activity_video_list.*
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener


/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class VideoListActivity : AppCompatActivity(), VideoListContract.View {
    override fun refreshData(data : Any) {
        val list = data as MutableList<VideoListItem>
         recyclerAdapter.addVideoListItem(list)
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

        val refreshLayout = findViewById(R.id.refreshLayout) as RefreshLayout
        refreshLayout.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshlayout: RefreshLayout) {
                refreshlayout.finishRefresh(2000/*,false*/)//传入false表示刷新失败
            }
        })
        refreshLayout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                refreshLayout.finishLoadMore(2000/*,false*/)//传入false表示刷新失败
            }
        })

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = VideoListRecyclerAdapter(mutableListOf())
        recyclerAdapter.setOnClickListener(object : RecyclerOnItemClickListener<VideoListItem> {
            override fun onClick(t: VideoListItem) {
                val intent = Intent(this@VideoListActivity, VideoDetailActivity::class.java)
                intent.putExtra("videoId", t.id)
                intent.putExtra("videoName", t.name)
                startActivity(intent)
            }
        })
        recyclerView.adapter = recyclerAdapter
    }
}