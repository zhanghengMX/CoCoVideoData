package com.henry.cocovideodata.classification

import com.henry.cocovideodata.base.BasePresenter
import com.henry.cocovideodata.base.BaseView
import com.henry.cocovideodata.bean.VideoDetail
import com.henry.cocovideodata.bean.VideoListItem
import com.henry.cocovideodata.bean.VideoUrl
import com.henry.cocovideodata.jsoup.WebMovie

/**
 * author：heng.zhang
 * date：2018/11/19
 * description：
 */
interface ClassificationContract {
    interface View : BaseView<Presenter> {
        fun refreshVideoListData(videoList : MutableList<VideoListItem>)
    }

    interface Presenter : BasePresenter<View> {
        fun getVideoListByTag(type: String, tag: String)
        fun loadMoreData()
        fun resetQequestData()
    }
}