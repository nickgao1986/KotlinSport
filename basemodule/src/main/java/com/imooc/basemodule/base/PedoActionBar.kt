package com.imooc.basemodule.base

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.pedo_actionbar.view.*


class PedoActionBar(context: Context, attrs:AttributeSet):
        RelativeLayout(context,attrs) {

    fun getLeftButton(): Button {
        return btn_left
    }

    fun setTitle(title:Any?) {
        if(title is Int) {
            tv_title.setText(title)
        }else if(title is String) {
            tv_title.text = title
        }
    }

    interface ActionBarListener {
        fun getTitleString():Any?

        fun getContentView():Int

        fun onLeftButtonClick()

    }

}