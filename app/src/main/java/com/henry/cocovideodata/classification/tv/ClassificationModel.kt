package com.henry.cocovideodata.classification

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.ClassificationVideoListBean
import com.henry.cocovideodata.bean.VideoListItem
import okhttp3.*
import java.io.IOException

/**
 * Copyright (c) 2018, 北京视达科科技有限责任公司 All rights reserved.
 * author：heng.zhang
 * date：2018/11/19
 * description：
 */
class ClassificationModel(override var responseListener: DataResponseListener) : BaseModel {

    private var currentType = ""
    private var currentTag = ""

    private val REQUEST_PAGE_LIMIT = 10  //单次请求的个数
    private var REQUEST_PAGE_START = 0   //一共请求的个数

    fun getTvClassification(dataType: String) {
        val request = Request.Builder()
                .url("https://movie.douban.com/j/search_tags?type=$dataType")
                .method("GET", null)
                .build()
        Log.i("getTvClassification", request.url().toString())
        OkHttpClient().newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val jsonString = response?.body()?.string()
                        val element: JsonObject = JsonParser().parse(jsonString) as JsonObject
                        val tags = element.get("tags").asJsonArray
                        val list = mutableListOf<String>()
                        for (tag in tags) {
                            list.add(tag.asString)
                        }
                        responseListener.onResult(dataType, list)
                    }
                })
    }

    fun getVideoListByTag(type: String, tag: String) {
        currentType = type
        currentTag = tag
        requestVideoList()
    }

    fun requestVideoList() {
        val request = Request.Builder()
                .url("https://movie.douban.com/j/search_subjects?type=$currentType&tag=$currentTag&sort=recommend&page_limit=$REQUEST_PAGE_LIMIT&page_start=$REQUEST_PAGE_START")
                .method("GET", null)
                .build()
        Log.i("requestVideoList", request.url().toString())
        OkHttpClient().newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val jsonString = response?.body()?.string()
                        val videoListBean = Gson().fromJson(jsonString, ClassificationVideoListBean::class.java)
                        val resultList = mutableListOf<VideoListItem>()
                        for (subject in videoListBean.subjects) {
                            resultList.add(VideoListItem(subject.id,
                                    subject.title, subject.rate, mutableListOf(),
                                    mutableListOf(), "", mutableListOf(), mutableListOf(subject.cover)))
                        }
                        REQUEST_PAGE_START += resultList.size
                        responseListener.onResult(BaseModel.DATA_TYPE_CLASSIFICATION_VIDEO_LIST, resultList)
                    }
                })
    }

    fun resetQequestParams() {
        REQUEST_PAGE_START = 0
        currentTag = ""
        currentType = ""
    }
}