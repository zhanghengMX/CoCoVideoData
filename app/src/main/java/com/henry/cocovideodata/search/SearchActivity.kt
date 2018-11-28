package com.henry.cocovideodata.search

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.RecyclerOnItemClickListener
import com.henry.cocovideodata.bean.VideoListItem
import com.henry.cocovideodata.detail.VideoDetailActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity() : AppCompatActivity(), SearchContract.View{

    override var presenter: SearchContract.Presenter = SearchPresenter(this)
    private lateinit var recyclerAdapter : SearchRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView() {
        searchRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = SearchRecyclerAdapter(mutableListOf())
        recyclerAdapter.setOnClickListener(object : RecyclerOnItemClickListener<VideoListItem> {
            override fun onClick(t: VideoListItem) {
                val intent = Intent(this@SearchActivity, VideoDetailActivity::class.java)
                intent.putExtra("videoId", t.id)
                intent.putExtra("videoName", t.name)
                startActivity(intent)
            }
        })
        searchRecyclerView.adapter = recyclerAdapter
    }
    override fun refreshData(data: Any) {
        val list = data as MutableList<VideoListItem>
        recyclerAdapter.addVideoListItem(list)    }

    fun onSearchClick(view : View) {
        recyclerAdapter.removeAllItem()
        presenter.loadData(searchEditText.text.toString())
    }
}
