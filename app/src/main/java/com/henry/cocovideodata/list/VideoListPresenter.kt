package com.henry.cocovideodata.list

import android.os.Bundle
import android.text.TextUtils
import com.henry.cocovideodata.App
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.Top250Video
import com.henry.cocovideodata.bean.VideoListItem

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class VideoListPresenter(override var view: VideoListContract.View) : VideoListContract.Presenter {

    private val model = VideoListModel(object : DataResponseListener {
        override fun onResult(dataType: String, result: Any) {
            if (TextUtils.equals(BaseModel.DATA_TYPE_TOP_250, dataType)) {
                val top250Video = result as Top250Video
                val videoList = ArrayList<VideoListItem>()
                for (subject in top250Video.subjects) {
                    val actorNames = ArrayList<String>()
                    val directorNames = ArrayList<String>()
                    for (actor in subject.casts) {
                        actorNames.add(actor.name)
                    }
                    for (director in subject.directors) {
                        directorNames.add(director.name)
                    }
                    videoList.add(VideoListItem(
                            subject.id,
                            subject.title,
                            subject.rating.average.toString(),
                            subject.genres,
                            actorNames,
                            subject.subtype,
                            directorNames,
                            mutableListOf(subject.images.large, subject.images.medium, subject.images.small)))
                }
                App.instance().postToMainLooper(object : Runnable {
                    override fun run() {
                        view.refreshData(videoList)
                    }

                })
            }
        }
    })

    init {
        view.presenter = this
    }

    override fun start(params : Bundle) {
        model.requestTop250List(0, 10)
    }

    override fun loadMoreData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}