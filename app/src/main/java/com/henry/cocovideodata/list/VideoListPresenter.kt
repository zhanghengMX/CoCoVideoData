package com.henry.cocovideodata.list

import okhttp3.*
import java.io.IOException

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class VideoListPresenter(override var view: VideoListContract.View) : VideoListContract.Presenter {

    init {
        view.presenter = this
    }
    override fun start() {
        loadData()
    }

    override fun loadData() {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
                .url("")
                .method("GET", null)
                .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
            }

        })
    }

    override fun loadMoreData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}