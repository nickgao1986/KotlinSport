package com.imooc.kotlinsportproject.guide.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.imooc.basemodule.util.ext.getVisible
import com.imooc.kotlinsportproject.R
import com.imooc.kotlinsportproject.guide.model.GuideImageModel


class GuideItemAdapter(layoutResId: Int, data: List<GuideImageModel>?) :
    BaseQuickAdapter<GuideImageModel, BaseViewHolder>(layoutResId, data), BaseQuickAdapter.OnItemClickListener {

    init {
        onItemClickListener = this
    }

    override fun convert(helper: BaseViewHolder, item: GuideImageModel) {
        val pic = helper.itemView.findViewById<View>(R.id.ivPhoto) as SimpleDraweeView
        val name = helper.itemView.findViewById<View>(R.id.name) as TextView
        pic.setActualImageResource(item.resource)
        name.text = item.name
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val ll_choose_pic = view.findViewById<View>(R.id.ll_choose_pic)
        if (ll_choose_pic.getVisible()) {
            ll_choose_pic.visibility = View.GONE
        } else {
            ll_choose_pic.visibility = View.VISIBLE
        }
    }
}
