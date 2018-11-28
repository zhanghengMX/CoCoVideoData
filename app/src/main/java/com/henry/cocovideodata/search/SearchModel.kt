package com.henry.cocovideodata.search

import android.util.Log
import com.google.gson.Gson
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.VideoListBean
import okhttp3.*
import java.io.IOException

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class SearchModel(override var responseListener: DataResponseListener) : BaseModel {
    fun search(keyword : String, start : Int, count : Int) {
        val request = Request.Builder()
                .url("https://api.douban.com/v2/movie/search?q=$keyword&start=$start&count=$count")
                .method("GET", null)
                .build()
        Log.i("SearchModel", request.url().toString())
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call?, response: Response?) {
                val jsonString  = response?.body()?.string()
                val resultList = Gson().fromJson(jsonString, VideoListBean::class.java)
                responseListener.onResult(BaseModel.DATA_TYPE_SEARCH_RESULT, resultList)
            }
        })
    }
}