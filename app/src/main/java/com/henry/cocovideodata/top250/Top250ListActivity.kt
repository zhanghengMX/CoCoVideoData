package com.henry.cocovideodata.top250

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.RecyclerOnItemClickListener
import com.henry.cocovideodata.bean.VideoListItem
import com.henry.cocovideodata.detail.VideoDetailActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_top250_list.*

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class Top250ListActivity : AppCompatActivity(), Top250ListContract.View {
    override fun refreshData(data : Any) {
        if (videoListRefreshLayout.state.isFooter) {
            videoListRefreshLayout.finishLoadMore()//传入false表示刷新失败
        }
        val list = data as MutableList<VideoListItem>
        recyclerAdapter.addVideoListItem(list)

    }

    override var presenter: Top250ListContract.Presenter = Top250ListPresenter(this)
    private lateinit var recyclerAdapter: Top250ListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top250_list)
        initView()
        presenter.start(Bundle())
    }
    private fun initView() {
        videoListRefreshLayout.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshlayout: RefreshLayout) {
                refreshlayout.finishRefresh(1000/*,false*/)//传入false表示刷新失败
            }
        })
        videoListRefreshLayout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                presenter.loadMoreData()
            }
        })
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = Top250ListRecyclerAdapter(mutableListOf())
        recyclerAdapter.setOnClickListener(object : RecyclerOnItemClickListener<VideoListItem> {
            override fun onClick(t: VideoListItem) {
                val intent = Intent(this@Top250ListActivity, VideoDetailActivity::class.java)
                intent.putExtra("videoId", t.id)
                intent.putExtra("videoName", t.name)
                startActivity(intent)
            }
        })
        recyclerView.adapter = recyclerAdapter
    }
}