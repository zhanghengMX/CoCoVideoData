package com.henry.cocovideodata.detail

import com.henry.cocovideodata.base.BasePresenter
import com.henry.cocovideodata.base.BaseView

/**
 * author：heng.zhang
 * date：2018/11/19
 * description：
 */
interface VideoDetailContract {
    interface View : BaseView<Presenter> {
        fun refreshVideoSource(data: Any)
        fun showSourceListDialog(list: Any)
    }

    interface Presenter : BasePresenter<View> {
        fun insertVideoItem()
    }
}