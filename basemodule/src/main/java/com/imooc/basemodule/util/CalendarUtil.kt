package com.imooc.basemodule.util

import java.text.SimpleDateFormat
import java.util.*

object CalendarUtil{
    val curTime:Long
    get() {
        val c = Calendar.getInstance()
        return c.timeInMillis
    }

    fun format4(timestamp: Long): String {
        val format = SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA)
        return format.format(Date(timestamp))
    }
}