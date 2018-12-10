package com.henry.cocovideodata.detail

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.henry.cocovideodata.R
import com.henry.cocovideodata.bean.VideoDetail
import com.henry.cocovideodata.jsoup.WebMovie
import com.henry.cocovideodata.top250.DetailSourceRecyclerAdapter
import com.henry.cocovideodata.utils.BmobHelper
import kotlinx.android.synthetic.main.activity_video_detail.*


//https://api.douban.com/v2/movie/subject/:id豆瓣详情接口
class VideoDetailActivity : AppCompatActivity(), VideoDetailContract.View {
    companion object {
        private val TAG = "VideoDetailActivity"
    }

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
        if (data is VideoDetail) {
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

    fun refreshVideoSource() = recyclerAdapter.addVideoSourceItem(presenter.getVideoUrlsCache())

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
                presenter.buildVideoUrlData(list[which])
                refreshVideoSource()
            }

        })
        dialogBuilder.create()
        dialogBuilder.show()
    }

    fun onUploadData(view: View) {
        if (presenter.getVideoIsUploadFlag()) {
            Toast.makeText(this, "此影片已上传", Toast.LENGTH_LONG).show()
            return
        }
        BmobHelper.uploadObject(presenter.getVideoDetailCache(), object : BmobHelper.BmobLoadDataListener {
            override fun onFailed() {
                Log.d(TAG, "upload video detail failed")
                Toast.makeText(this@VideoDetailActivity, "影片信息上传失败", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d(TAG, "upload video detail success")
                Toast.makeText(this@VideoDetailActivity, "影片信息上传成功", Toast.LENGTH_SHORT).show()
            }
        })
        BmobHelper.batchUploadObject(presenter.getVideoUrlsCache(), object : BmobHelper.BmobLoadDataListener {
            override fun onSuccess() {
                Log.d(TAG, "upload video url success")
                Toast.makeText(this@VideoDetailActivity, "播放串上传成功", Toast.LENGTH_LONG).show()
            }

            override fun onFailed() {
                Log.d(TAG, "upload video url failed")
                Toast.makeText(this@VideoDetailActivity, "播放串上传失败", Toast.LENGTH_LONG).show()
            }
        })
    }
}
