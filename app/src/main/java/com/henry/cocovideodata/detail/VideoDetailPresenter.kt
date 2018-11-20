package com.henry.cocovideodata.detail

import android.os.Bundle
import com.henry.cocovideodata.base.DataResponseListener

/**
 * Copyright (c) 2018, 北京视达科科技有限责任公司 All rights reserved.
 * author：heng.zhang
 * date：2018/11/19
 * description：
 */
class VideoDetailPresenter(override var view: VideoDetailContract.View) : VideoDetailContract.Presenter{
    init{
        view.presenter = this
    }
    private val model = VideoDetailModel(object : DataResponseListener {
        override fun onResult(dataType: String, result: Any) {

        }
    })
    override fun insertVideoItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start(params : Bundle) {
        val videoId = params.getString("videoId")
        model.getVideoDetail(videoId)
    }
}