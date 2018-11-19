package com.henry.cocovideodata.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.henry.cocovideodata.R


//https://api.douban.com/v2/movie/subject/:id豆瓣详情接口
class VideoDetailActivity(override var presenter: VideoDetailContract.Presenter) : AppCompatActivity(), VideoDetailContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        presenter.start(intent.extras)
    }


    override fun refreshData(data: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
