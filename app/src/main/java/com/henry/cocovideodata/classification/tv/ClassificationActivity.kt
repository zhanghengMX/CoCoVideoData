package com.henry.cocovideodata.classification

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.henry.cocovideodata.R
import com.henry.cocovideodata.adapter.VideoListRecyclerAdapter
import com.henry.cocovideodata.base.RecyclerOnItemClickListener
import com.henry.cocovideodata.bean.VideoListItem
import com.henry.cocovideodata.detail.VideoDetailActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_tv_classification.*

class ClassificationActivity : AppCompatActivity(), ClassificationContract.View {

    private val TAG = "TvClassification"
    override var presenter: ClassificationContract.Presenter = ClassificationPresenter(this)

    private var type = ""
    private lateinit var recyclerAdapter: VideoListRecyclerAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_classification)
        initView()
        type = intent.getStringExtra("classification_type")
        val params = Bundle()
        params.putString("classification_type", type)
        presenter.start(params)
    }

    private fun initView() {
        spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item)
        classificationSpinner.adapter = spinnerAdapter
        classificationSpinner.onItemSelectedListener = SinnerSelectListener()

        classificationRefreshLayout.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshlayout: RefreshLayout) {
                getVideoBySelectedTag()
                refreshlayout.finishRefresh(1000/*,false*/)//传入false表示刷新失败
            }
        })
        classificationRefreshLayout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                presenter.loadMoreData()
            }
        })
        classificationRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        classificationRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = VideoListRecyclerAdapter(mutableListOf())
        recyclerAdapter.setOnClickListener(object : RecyclerOnItemClickListener<VideoListItem> {
            override fun onClick(t: VideoListItem) {
                val intent = Intent(this@ClassificationActivity, VideoDetailActivity::class.java)
                intent.putExtra("videoId", t.id)
                intent.putExtra("videoName", t.name)
                startActivity(intent)
            }
        })
        classificationRecyclerView.adapter = recyclerAdapter
    }


    override fun refreshData(data: Any) {
        val tagList = data as MutableList<String>
        spinnerAdapter.addAll(tagList)
    }


    inner class SinnerSelectListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            getVideoBySelectedTag()
        }
    }

    private fun getVideoBySelectedTag() {
        recyclerAdapter.clearVideoListItem()
        presenter.resetQequestData()
        presenter.getVideoListByTag(type, classificationSpinner.selectedItem as String)
    }

    override fun refreshVideoListData(videoList: MutableList<VideoListItem>) {
        if (classificationRefreshLayout.state.isFooter) {
            classificationRefreshLayout.finishLoadMore()//传入false表示刷新失败
        }
        recyclerAdapter.addVideoListItem(videoList)
    }

}