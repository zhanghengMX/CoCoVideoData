package com.henry.cocovideodata.bean

import cn.bmob.v3.BmobObject

data class Video(val title : String,
                 val average : String,
                 val year :String,
                 val countries : String,
                 val genres : String,
                 val collectCount : String,
                 val casts : String,
                 val summary : String,
                 val subType : String,
                 val directors : String) : BmobObject()