package com.henry.cocovideodata.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.RecyclerOnItemClickListener
import kotlinx.android.synthetic.main.activity_video_list.*

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class VideoListActivity : AppCompatActivity(), VideoListContract.View {
    override var presenter: VideoListContract.Presenter = VideoListPresenter(this)
    private lateinit var recyclerAdapter: VideoListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)
        initView()
        presenter.start()
    }
    private fun initView() {
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = VideoListRecyclerAdapter(arrayListOf("item01", "item02", "item03"))
        recyclerAdapter.setOnClickListener(object : RecyclerOnItemClickListener<String> {
            override fun onClick(t: String) {
                Log.d("HR", t)
            }
        })
        recyclerView.adapter = recyclerAdapter
    }
}