package com.henry.cocovideodata.classification

import android.os.Bundle
import android.text.TextUtils
import com.henry.cocovideodata.App
import com.henry.cocovideodata.base.BaseModel
import com.henry.cocovideodata.base.DataResponseListener
import com.henry.cocovideodata.bean.VideoListItem
import com.henry.cocovideodata.classification.ClassificationContract
import com.henry.cocovideodata.classification.ClassificationModel
import org.jsoup.Connection

/**
 * Copyright (c) 2018, 北京视达科科技有限责任公司 All rights reserved.
 * author：heng.zhang
 * date：2018/11/19
 * description：
 */
class ClassificationPresenter(override var view: ClassificationContract.View) : ClassificationContract.Presenter {


    init {
        view.presenter = this
    }

    private val model = ClassificationModel(object : DataResponseListener {
        override fun onResult(dataType: String, result: Any) {
            App.instance().postToMainLooper(object : Runnable {
                override fun run() {
                    if (TextUtils.equals(dataType, BaseModel.DATA_TYPE_CLASSIFICATION_TV)
                    || TextUtils.equals(dataType, BaseModel.DATA_TYPE_CLASSIFICATION_MOVIE)) {
                        view.refreshData(result)
                    } else if (TextUtils.equals(dataType, BaseModel.DATA_TYPE_CLASSIFICATION_VIDEO_LIST)) {
                        view.refreshVideoListData(result as MutableList<VideoListItem>)
                    }
                }

            })

        }
    })

    override fun start(params: Bundle) {
        val type = params.getString("classification_type")
        model.getTvClassification(type)
    }

    override fun getVideoListByTag(type: String, tag: String) {
        model.getVideoListByTag(type, tag)
    }

    override fun loadMoreData() {
        model.requestVideoList()
    }

    override fun resetQequestData() {
        model.resetQequestParams()
    }
}