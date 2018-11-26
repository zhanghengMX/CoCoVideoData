package com.henry.cocovideodata.utils

import android.util.Log
import cn.bmob.v3.BmobBatch
import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BatchResult
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListListener
import cn.bmob.v3.listener.SaveListener
import com.henry.cocovideodata.bean.VideoUrl

/**
 * Created by henry_zhang on 2018/11/25.
 */
class BmobHelper {
    interface BmobLoadDataListener {
        fun onSuccess()
        fun onFailed()
    }

    companion object {
        private val TAG = "BmobHelper"

        fun uploadObject(videoData : BmobObject, lsr : BmobLoadDataListener){
            videoData.save(object : SaveListener<String>(){
                override fun done(p0: String?, p1: BmobException?) {
                    Log.d(TAG, "uploadObject ------->   objectId: $p0,  BmobException :  $p1")
                    if (null == p1) {
                        lsr.onSuccess()
                    } else {
                        p1.printStackTrace()
                        lsr.onFailed()
                    }
                }
            })
        }

        fun batchUploadObject(dataList: MutableList<*>, lsr: BmobLoadDataListener){
            val list = mutableListOf<BmobObject>()
            for (data in dataList) {
                list.add(data as BmobObject)
            }
            BmobBatch().insertBatch(list)
                    .doBatch(object : QueryListListener<BatchResult>() {
                        override fun done(p0: MutableList<BatchResult>?, p1: BmobException?) {
                            if (p1 == null) {
                                for (i in 0..p0!!.size - 1) {
                                    val result = p0.get(i)
                                    val ex = result.getError()
                                    if (ex == null) {
                                        Log.i(TAG, "第${i}个数据批量添加成功：${result.getCreatedAt()}, ${result.getObjectId()}")
                                    } else {
                                        Log.e(TAG, "第${i}个数据批量添加失败: ${ex!!.message},${ex!!.getErrorCode()}")
                                    }
                                }
                            } else {
                                Log.e(TAG, "失败：" + p1.message + "," + p1.errorCode)
                            }
                        }

                    })
        }
    }
}