package com.henry.cocovideodata.top250

import com.henry.cocovideodata.base.BasePresenter
import com.henry.cocovideodata.base.BaseView

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
interface Top250ListContract {
    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter<View> {
        fun loadMoreData()
    }
}