package com.imooc.kotlinsportproject.guide

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.imooc.basemodule.base.BaseActivity
import com.imooc.basemodule.util.Util
import com.imooc.kotlinsportproject.R
import com.imooc.kotlinsportproject.guide.adapter.GuideItemAdapter
import com.imooc.kotlinsportproject.guide.decoration.GuideSpaceItemDecoration
import com.imooc.kotlinsportproject.guide.model.GuideImageModel
import kotlinx.android.synthetic.main.pedo_choose_item_layout.*
import java.util.*

class GuideChooseItemActivity : BaseActivity() {

    private lateinit var mRecyclerView: RecyclerView

    var array = intArrayOf(
        R.drawable.pedo_interesting_choose1,
        R.drawable.pedo_interesting_choose2,
        R.drawable.pedo_interesting_choose3,
        R.drawable.pedo_interesting_choose4,
        R.drawable.pedo_interesting_choose5,
        R.drawable.pedo_interesting_choose6,
        R.drawable.pedo_interesting_choose7,
        R.drawable.pedo_interesting_choose8,
        R.drawable.pedo_interesting_choose9
    )
    var strArray = arrayOf("健身", "跑步", "高效塑性", "科学饮食", "骑行", "篮球", "体态改善", "健康小习惯", "马拉松")

    override fun getContentView(): Int {
        return R.layout.pedo_choose_item_layout
    }


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleRecycleView()
        btn.setOnClickListener {
        }
    }


    private fun handleRecycleView() {
        val layoutManager = GridLayoutManager(mContext, 3)
        rv_list.layoutManager = layoutManager

        val list = ArrayList<GuideImageModel>()
        for (i in 0..8) {
            list.add(GuideImageModel(array[i], strArray[i]))
        }
        val adapter = GuideItemAdapter(R.layout.pedo_layout_dynamic_image_item, list)
        rv_list.adapter = adapter
        val spacing = Util.dp2px(this,7f)
        rv_list.addItemDecoration(GuideSpaceItemDecoration(spacing))

    }


    override fun getTitleString(): Any {
        return ""
    }
}
