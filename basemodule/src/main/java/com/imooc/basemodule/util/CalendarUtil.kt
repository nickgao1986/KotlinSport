package com.imooc.basemodule.util

import java.util.*

object CalendarUtil{
    val curTime:Long
    get() {
        val c = Calendar.getInstance()
        return c.timeInMillis
    }
}