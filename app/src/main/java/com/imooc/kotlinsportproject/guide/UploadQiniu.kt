package com.imooc.kotlinsportproject.guide

import android.arch.lifecycle.GenericLifecycleObserver
import android.util.Log
import com.imooc.basemodule.util.CalendarUtil
import com.imooc.basemodule.util.GlobalCont
import com.qiniu.android.storage.UploadManager
import com.qiuniu.Auth
import com.util.LogUtil

object UploadQiniu {

    fun uploadToQiniu(path:String) {
        var uploadManager = UploadManager()
        var key = "avatar_"+CalendarUtil.curTime
        val accessKey = GlobalCont.accessKey
        val secretKey = GlobalCont.secretKey

        uploadManager.put(path,key,Auth.create(accessKey,secretKey)
            .uploadToken("imooc_sport"),{
            key,info,res->
                if(info.isOK) {
                    val headpicPath = GlobalCont.qiNiuStorePath+key
                    LogUtil.d("<<<<headpicPath")
                }
        },null)

    }
}