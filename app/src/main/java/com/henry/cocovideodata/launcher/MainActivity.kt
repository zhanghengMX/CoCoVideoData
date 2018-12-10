package com.henry.cocovideodata.launcher

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.henry.cocovideodata.R
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.RecyclerOnItemClickListener
import com.henry.cocovideodata.classification.ClassificationActivity
import com.henry.cocovideodata.search.SearchActivity
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
        getAndroiodScreenProperty()
    }

    private fun initView() {
        recyclerAdapter = MainPageRecyclerAdapter(items)
        recyclerAdapter.setOnClickListener(object : RecyclerOnItemClickListener<String> {
            override fun onClick(t: String) {
                openActivityByName(t)
            }
        })
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.main_page_custom_divider))
        mainPageRecyclerView.addItemDecoration(divider)
        mainPageRecyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        mainPageRecyclerView.adapter = recyclerAdapter
    }

    private fun openActivityByName(pageName : String) {
        val intent = Intent()
        when(pageName) {
            items[0] -> intent.setClass(this, Top250ListActivity::class.java)
            items[1] -> intent.setClass(this, SearchActivity::class.java)
            items[2] -> {
                intent.setClass(this, ClassificationActivity::class.java)
                intent.putExtra("classification_type", BaseModel.DATA_TYPE_CLASSIFICATION_MOVIE)
            }
            items[3] -> {
                intent.setClass(this, ClassificationActivity::class.java)
                intent.putExtra("classification_type", BaseModel.DATA_TYPE_CLASSIFICATION_TV)
            }
        }
        startActivity(intent)
    }

    fun getAndroiodScreenProperty() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        val width = dm.widthPixels // 屏幕宽度（像素）
        val height = dm.heightPixels // 屏幕高度（像素）
        val density = dm.density //屏幕密度（0.75 / 1.0 / 1.5）
        val densityDpi = dm.densityDpi //屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        val screenWidth =(width/density) //屏幕宽度(dp)
        val screenHeight =(height/density) //屏幕高度(dp)
        Log.e("MainActivity", "$screenWidth======$screenHeight")
    }
}
