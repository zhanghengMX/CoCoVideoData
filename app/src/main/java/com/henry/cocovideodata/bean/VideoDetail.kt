package com.henry.cocovideodata.bean

import cn.bmob.v3.BmobObject

data class VideoDetail(val doubanId: String,
                       val name: String,
                       val average: String,
                       val year : String,
                       val countries : MutableList<String>,
                       val genres: MutableList<String>,
                       val casts: MutableList<MutableMap<String, String>>,
                       val summary : String,
                       val subType: String,
                       val directors: MutableList<MutableMap<String, String>>,
                       val images: MutableMap<String, String>) : BmobObject()