package com.henry.cocovideodata.list

import com.henry.cocovideodata.base.BasePresenter
import com.henry.cocovideodata.base.BaseView

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
interface VideoListContract {
    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter<View> {

    }
}