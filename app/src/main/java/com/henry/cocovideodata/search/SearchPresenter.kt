package com.henry.cocovideodata.search

import android.os.Bundle
import com.henry.cocovideodata.App
import com.henry.cocovideodata.base.DataResponseListener

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
class SearchPresenter(override var view: SearchContract.View) : SearchContract.Presenter {
    companion object {
        private val REQUEST_VIDEO_COUNT = 10  //单次请求的个数
        private var REQUEST_TOTAL_COUNT = 0   //一共请求的个数
    }

    private val model = SearchModel(object : DataResponseListener {
        override fun onResult(dataType: String, result: Any) {
            REQUEST_TOTAL_COUNT += REQUEST_VIDEO_COUNT

                App.instance().postToMainLooper(object : Runnable {
                    override fun run() {
                        view.refreshData(result)
                    }

                })
            }
    })

    init {
        view.presenter = this
    }

    override fun start(params : Bundle) {
    }

    override fun loadData(keyword : String) {
        model.search(keyword, REQUEST_TOTAL_COUNT, REQUEST_VIDEO_COUNT)
    }
}