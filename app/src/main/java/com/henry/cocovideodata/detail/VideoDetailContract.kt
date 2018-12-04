package com.henry.cocovideodata.detail

import com.henry.cocovideodata.base.BasePresenter
import com.henry.cocovideodata.base.BaseView
import com.henry.cocovideodata.bean.VideoDetail
import com.henry.cocovideodata.bean.VideoUrl
import com.henry.cocovideodata.jsoup.WebMovie

/**
 * author：heng.zhang
 * date：2018/11/19
 * description：
 */
interface VideoDetailContract {
    interface View : BaseView<Presenter> {
        fun showSourceListDialog(list: Any)
    }

    interface Presenter : BasePresenter<View> {
        fun buildVideoUrlData(webMovie : WebMovie)
        fun getVideoDetailCache() : VideoDetail
        fun getVideoUrlsCache() : MutableList<VideoUrl>
        fun getVideoIsUploadFlag() : Boolean
    }
}