package com.henry.cocovideodata.launcher

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.RecyclerOnItemClickListener
import com.henry.cocovideodata.top250.MainPageRecyclerAdapter
import com.henry.cocovideodata.top250.Top250ListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val items = mutableListOf("电影排行榜", "搜索", "电影分类","电视分类")
    private lateinit var recyclerAdapter : MainPageRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        recyclerAdapter = MainPageRecyclerAdapter(items)
        recyclerAdapter.setOnClickListener(object : RecyclerOnItemClickListener<String> {
            override fun onClick(t: String) {
                openActivityByName(t)
            }
        })
        mainPageRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mainPageRecyclerView.layoutManager = LinearLayoutManager(this)
        mainPageRecyclerView.adapter = recyclerAdapter
    }

    private fun openActivityByName(pageName : String) {
        val intent = Intent()
        when(pageName) {
            items[0] -> intent.setClass(this, Top250ListActivity::class.java)
        }
        startActivity(intent)
    }
}
