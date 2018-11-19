package com.henry.cocovideodata.bean

import cn.bmob.v3.BmobObject

data class VideoListItem(val id: String,
                         val name: String,
                         val average: String,
                         val genres: MutableList<String>,
                         val casts: MutableList<String>,
                         val subType: String,
                         val directors: MutableList<String>,
                         val images: MutableList<String>) : BmobObject()