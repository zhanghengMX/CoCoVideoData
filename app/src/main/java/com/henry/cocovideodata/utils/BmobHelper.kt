package com.henry.cocovideodata.utils

import android.util.Log
import cn.bmob.v3.BmobObject
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener

/**
 * Created by henry_zhang on 2018/11/25.
 */
class BmobHelper {
    companion object {
        fun uploadObject(videoData : BmobObject){
            videoData.save(object : SaveListener<String>(){
                override fun done(p0: String?, p1: BmobException?) {
                    Log.d("HR", "objectId: $p0,  BmobException :  $p1")
                }

            })
        }
    }
}