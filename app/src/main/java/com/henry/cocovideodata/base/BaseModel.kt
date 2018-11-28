package com.henry.cocovideodata.base

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
interface BaseModel {
    var responseListener : DataResponseListener
    companion object{
        val DATA_TYPE_TOP_250 = "@data-type-top-250"
        val DATA_TYPE_VIDEO_DETAIL = "@data-type-video_detail"
        val DATA_TYPE_VIDEO_SOURCE = "@data-type-video_source"
        val DATA_TYPE_SEARCH_RESULT = "@data-type-searche_result"

    }
}