package com.henry.cocovideodata.base

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
interface BaseView<T> {
    var presenter : T
    fun refreshData(data : Any)
}