package com.henry.cocovideodata.detail

import android.util.Log
import com.google.gson.Gson
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.Top250Video
import com.henry.cocovideodata.bean.Video
import com.henry.cocovideodata.bean.VideoDetailBean
import com.henry.cocovideodata.jsoup.SourceCatcher
import okhttp3.*
import java.io.IOException

/**
 * Copyright (c) 2018, 北京视达科科技有限责任公司 All rights reserved.
 * author：heng.zhang
 * date：2018/11/19
 * description：
 */
class VideoDetailModel(override var responseListener: DataResponseListener) : BaseModel{

    fun getVideoDetail(videoId : String) {
        val request = Request.Builder()
                .url("https://api.douban.com/v2/movie/subject/$videoId")
                .method("GET", null)
                .build()
        OkHttpClient().newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val jsonString  = response?.body()?.string()
                        val videoDetailBean = Gson().fromJson(jsonString, VideoDetailBean::class.java)
                        val actorList = mutableListOf<MutableMap<String, String>>()
                        for (cast in videoDetailBean.casts) {
                            val map = mutableMapOf<String, String>()
                            map.put("actor_id", cast.id)
                            map.put("actor_name", cast.name)
                            map.put("actor_head", cast.avatars.small)
                            actorList.add(map)
                        }

                        val directorList = mutableListOf<MutableMap<String, String>>()
                        for (director in videoDetailBean.directors) {
                            val map = mutableMapOf<String, String>()
                            map.put("director_id", director.id)
                            map.put("director_name", director.name)
                            map.put("director_head", director.avatars.small)
                            directorList.add(map)
                        }
                        val images = mutableMapOf<String, String>()
                        images.put("large", videoDetailBean.images.large)
                        images.put("medium", videoDetailBean.images.medium)
                        images.put("small", videoDetailBean.images.small)
                        val video = Video(videoDetailBean.id, videoDetailBean.title,
                                videoDetailBean.rating.average, videoDetailBean.year,
                                videoDetailBean.countries, videoDetailBean.genres,
                                actorList, videoDetailBean.summary,
                                videoDetailBean.subtype, directorList, images)

                        responseListener.onResult(BaseModel.DATA_TYPE_VIDEO_DETAIL, video)
                    }
                })
    }

    fun getVideoSource(videoName : String) {
        val sourceCatcher = SourceCatcher(SourceCatcher.OK_SEARCH_URL, SourceCatcher.OK_BASE_URL)
        Thread(object : Runnable{
            override fun run() {
                val list = sourceCatcher.start(videoName)
                responseListener.onResult(BaseModel.DATA_TYPE_VIDEO_SOURCE, list)
            }

        }).start()
    }
}