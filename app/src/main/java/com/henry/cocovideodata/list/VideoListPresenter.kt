package com.henry.cocovideodata.list

import android.text.TextUtils
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.Top250Video

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class VideoListPresenter(override var view: VideoListContract.View) : VideoListContract.Presenter {

    private val model = VideoListModel(object : DataResponseListener {
        override fun onResult(dataType: String, result: Any) {
            if (TextUtils.equals(VideoListModel.DATA_TYPE_TOP_250, dataType)) {
                val top250Video = result as Top250Video
                //TODO 将数据传递给View，刷新UI显示
            }
        }
    })

    init {
        view.presenter = this
    }

    override fun start() {
        loadData()
    }

    override fun loadData() {
        model.requestTop250List(0, 10)
    }

    override fun loadMoreData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}