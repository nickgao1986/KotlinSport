package com.imooc.basemodule.util

import android.content.Context
import java.io.File

class Util {

    companion object {
        fun dp2px(context: Context, dpValue:Float):Int {
            return (dpValue* getDensity(context) + 0.5f).toInt()
        }


        fun getDensity(context:Context):Float{
            return context.resources.displayMetrics.density
        }

        // 初始化路径
        fun initDataDirectory(path: String) {
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
        }
    }
}