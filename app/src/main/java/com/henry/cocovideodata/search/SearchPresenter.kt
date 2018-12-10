package com.henry.cocovideodata.search

import android.os.Bundle
import android.text.TextUtils
import com.henry.cocovideodata.App
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.VideoListBean
import com.henry.cocovideodata.bean.VideoListItem

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class SearchPresenter(override var view: SearchContract.View) : SearchContract.Presenter {
    companion object {
        private val REQUEST_VIDEO_COUNT = 10  //单次请求的个数
        private var REQUEST_TOTAL_COUNT = 0   //一共请求的个数
    }

    private val model = SearchModel(object : DataResponseListener {
        override fun onResult(dataType: String, result: Any) {
            if (TextUtils.equals(BaseModel.DATA_TYPE_SEARCH_RESULT, dataType)) {
                val searchVideoList = result as VideoListBean
                val videoList = ArrayList<VideoListItem>()
                for (subject in searchVideoList.subjects) {
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
                            mutableListOf(subject.images.medium, subject.images.large, subject.images.small)))
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
    }

    override fun loadData(keyword : String) {
        model.search(keyword, REQUEST_TOTAL_COUNT, REQUEST_VIDEO_COUNT)
    }
}