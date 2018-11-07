package com.henry.cocovideodata.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.henry.cocovideodata.R

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class VideoListActivity : AppCompatActivity(), VideoListContract.View {
    override var presenter: VideoListContract.Presenter = VideoListPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)
        presenter.start()
    }
}