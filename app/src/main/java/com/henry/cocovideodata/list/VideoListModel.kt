package com.henry.cocovideodata.list

import android.util.Log
import com.google.gson.Gson
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.Top250Video
import okhttp3.*
import java.io.IOException

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class VideoListModel(override var responseListener: DataResponseListener) : BaseModel {
    fun requestTop250List(start : Int, count : Int) {
        val request = Request.Builder()
                .url("https://api.douban.com/v2/movie/top250?start=$start&count=$count")
                .method("GET", null)
                .build()
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call?, response: Response?) {
                val jsonString  = response?.body()?.string()
                val top250Video = Gson().fromJson(jsonString, Top250Video::class.java)
                responseListener.onResult(BaseModel.DATA_TYPE_TOP_250, top250Video)
            }
        })
    }
}