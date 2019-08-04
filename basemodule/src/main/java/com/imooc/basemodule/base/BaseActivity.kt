package com.imooc.basemodule.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import com.imooc.basemodule.R

abstract class BaseActivity:AppCompatActivity(), PedoActionBar.ActionBarListener{

    protected lateinit var mContext: Activity
    protected lateinit var mBaseView:ViewGroup
    protected lateinit var mActionBar: PedoActionBar
    protected lateinit var mContentLayout:ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        mBaseView = LayoutInflater.from(this).inflate(R.layout.pedo_activity_base,null)
        as ViewGroup

        mContentLayout = mBaseView.findViewById(R.id.body) as ViewGroup
        val sub = mBaseView.findViewById<View>(R.id.actionbar_title) as ViewStub

        getTitleString()?.run {
            mActionBar = sub.inflate() as PedoActionBar
            mActionBar.setTitle(getTitleString())
            mActionBar.getLeftButton().setOnClickListener{
                onLeftButtonClick()
            }
        }

        setContentView(getContentView())

    }

    override fun setContentView(view:View?) {
        view?.let {
            mBaseView.removeView(mContentLayout)
            mBaseView.addView(view,mContentLayout?.layoutParams)
            mContentLayout = view as ViewGroup
        }
        super.setContentView(mBaseView)
    }

    override fun setContentView(layoutResID: Int) {
        if(layoutResID == 0) {
            setContentView(null)
        }else{
            setContentView(LayoutInflater.from(this).inflate(layoutResID,null))
        }
        super.setContentView(layoutResID)
    }


    override fun onLeftButtonClick() {
    }
}