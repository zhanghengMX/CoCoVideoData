package com.henry.cocovideodata.detail

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.henry.cocovideodata.R
import com.henry.cocovideodata.bean.Video
import com.henry.cocovideodata.jsoup.WebMovie
import com.henry.cocovideodata.list.DetailSourceRecyclerAdapter
import kotlinx.android.synthetic.main.activity_video_detail.*


//https://api.douban.com/v2/movie/subject/:id豆瓣详情接口
class VideoDetailActivity : AppCompatActivity(), VideoDetailContract.View {
    companion object {
        private val TAG = "VideoDetailActivity"
    }

    private lateinit var videoData : Video

    override var presenter: VideoDetailContract.Presenter = VideoDetailPresenter(this)
    private lateinit var recyclerAdapter: DetailSourceRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        initView()
        presenter.start(intent.extras)
    }

    private fun initView() {
        detailRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        detailRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = DetailSourceRecyclerAdapter(mutableListOf())
        detailRecyclerView.adapter = recyclerAdapter
    }

    override fun refreshData(data: Any) {
        if (data is Video) {
            videoData = data
            Glide.with(this)
                    .load(data.images["medium"])
                    .into(detailPosterIv)
            detailVideoName.text = data.name
            var actors = StringBuilder()
            for (actorMap in data.casts) {
                actors.append(actorMap["actor_name"]).append(" ")
            }
            detailActorTv.text = actors

            var directors = StringBuilder()
            for (directorMap in data.directors) {
                directors.append(directorMap["director_name"]).append(" ")
            }
            detailDirectorTv.text = directors

            detailYearTv.text = data.year
            detailAreaTv.text = data.countries.toString()
            detailAverageTv.text = data.average
            detailSummaryTv.text = data.summary
        }
    }

    override fun refreshVideoSource(data: Any) {
    }

    override fun showSourceListDialog(list: Any) {
        val titles = mutableListOf<String>()
        val mutableList = list as MutableList<WebMovie>
        for (webMovie in mutableList) {
            titles.add(webMovie.title)
        }
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setItems(titles.toTypedArray(), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                Log.d(TAG, "which : $which")
                buildUploadData(list[which])

            }

        })
        dialogBuilder.create()
        dialogBuilder.show()
    }

    private fun buildUploadData(selectedItem : WebMovie) {
        for (indexSource in selectedItem.playSource) {
            val urls = mutableListOf<String>()
            for (source in indexSource.sourceList) {
                urls.add(source.url)
            }
            videoData.urls.add(indexSource.sourceIndex, urls)
        }
    }

    fun onUploadData(view: View) {
        Log.i("HR", "onUploadData")
        val aaa = videoData
    }
}
