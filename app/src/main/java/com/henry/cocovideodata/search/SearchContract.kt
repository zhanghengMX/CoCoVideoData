package com.henry.cocovideodata.search

import com.henry.cocovideodata.base.BasePresenter
import com.henry.cocovideodata.base.BaseView

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
interface SearchContract {
    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter<View> {
        fun loadData(keywprd : String)
    }
}