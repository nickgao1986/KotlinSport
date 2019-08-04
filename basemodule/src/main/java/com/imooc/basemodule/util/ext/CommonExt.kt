package com.imooc.basemodule.util.ext

import android.view.View


fun View.onClick(method:() -> Unit):View{
    setOnClickListener { method() }
    return this
}

fun View.setVisible(visible:Boolean){
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.getVisible() : Boolean {
    return if (this.visibility == View.VISIBLE) true else false
}