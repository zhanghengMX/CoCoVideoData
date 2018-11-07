package com.henry.cocovideodata.base

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
interface BasePresenter<T> {
    var view : T

    fun init()

    fun loadData()

    fun loadMoreData()
}