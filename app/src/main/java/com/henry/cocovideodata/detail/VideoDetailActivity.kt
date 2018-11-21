package com.henry.cocovideodata.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.henry.cocovideodata.R
import com.henry.cocovideodata.bean.Video
import com.henry.cocovideodata.jsoup.SourceCatcher
import kotlinx.android.synthetic.main.activity_video_detail.*


//https://api.douban.com/v2/movie/subject/:id豆瓣详情接口
class VideoDetailActivity : AppCompatActivity(), VideoDetailContract.View {

    override var presenter: VideoDetailContract.Presenter = VideoDetailPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        presenter.start(intent.extras)
    }


    override fun refreshData(data: Any) {
        runOnUiThread {
            if (data is Video) {
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
    }

    fun onUploadData(view : View) {
        Log.i("HR", "onUploadData")
        val sourceCatcher = SourceCatcher(SourceCatcher.OK_SEARCH_URL, SourceCatcher.OK_BASE_URL)
        Thread(object : Runnable{
            override fun run() {
                val list = sourceCatcher.start("肖申克的救赎")
                Log.i("HR", "onUploadData")
            }

        }).start()
    }
}
