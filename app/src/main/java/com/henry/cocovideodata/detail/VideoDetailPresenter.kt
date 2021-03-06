package com.henry.cocovideodata.detail

import android.os.Bundle
import android.text.TextUtils
import com.henry.cocovideodata.App
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.VideoDetail
import com.henry.cocovideodata.bean.VideoUrl
import com.henry.cocovideodata.jsoup.WebMovie

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
            App.instance().postToMainLooper(object : Runnable {
                override fun run() {
                    if (TextUtils.equals(dataType, BaseModel.DATA_TYPE_VIDEO_DETAIL)) {
                        view.refreshData(result)
                    } else if (TextUtils.equals(dataType, BaseModel.DATA_TYPE_VIDEO_SOURCE)) {
                        view.showSourceListDialog(result)
                    }
                }

            })

        }
    })

    override fun getVideoIsUploadFlag(): Boolean {
        return model.videoIsUpload
    }
    override fun getVideoDetailCache(): VideoDetail = model.videoData

    override fun getVideoUrlsCache(): MutableList<VideoUrl> = model.videoUrls

    override fun buildVideoUrlData(webMovie: WebMovie) {
        for (indexSource in webMovie.playSource) {
            val urls = mutableListOf<String>()
            indexSource.sourceList.mapTo(urls) { it.url }
            model.videoUrls.add(VideoUrl(model.videoData.doubanId, model.videoData.name, indexSource.sourceIndex, urls))
        }
    }

    override fun start(params : Bundle) {
        val videoId = params.getString("videoId")
        model.checkVideoIsUpload(videoId)
        model.getVideoDetail(videoId)

        val videoName = params.getString("videoName")
        model.getVideoSource(videoName)
    }
}