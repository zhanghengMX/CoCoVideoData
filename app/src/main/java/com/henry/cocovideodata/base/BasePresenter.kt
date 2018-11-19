package com.henry.cocovideodata.base

import android.os.Bundle

/**
 * author：heng.zhang
 * date：2018/11/7
 * description：
 */
interface BasePresenter<T> {
    var view : T

    fun start(params: Bundle)
}