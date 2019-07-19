package com.imooc.basemodule.base

import android.content.Context

class Util {

    companion object {
        fun dp2px(context: Context, dpValue:Float):Int {
            return (dpValue*getDensity(context) + 0.5f).toInt()
        }


        fun getDensity(context:Context):Float{
            return context.resources.displayMetrics.density
        }
    }
}