package com.imooc.kotlinsportproject.welcome

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import com.imooc.basemodule.base.BaseActivity
import com.imooc.kotlinsportproject.R
import com.imooc.kotlinsportproject.guide.SetAvatartActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.pedo_welcome_layout.*
import org.jetbrains.anko.startActivity




class WelcomeActivity :BaseActivity() {

    companion object{
        val ANIM_TIME = 3000
    }
    override fun getTitleString(): Any? {
        return null
    }

    override fun getContentView(): Int {
        return R.layout.pedo_welcome_layout
    }

    override fun onLeftButtonClick() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAnimation()
        addPermission()
    }

    fun startAnimation() {
        val animationX = ObjectAnimator.ofFloat(iv_entry,
            "ScaleX",1f,1.15f)
        val animationY = ObjectAnimator.ofFloat(iv_entry,
            "ScaleY",1f,1.15f)


        val set = AnimatorSet()
        set.setDuration(ANIM_TIME.toLong()).playTogether(animationX,animationY)
        set.start()

        set.addListener(object :AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                       }
        })

        counter_down_view.start()
        counter_down_view.setOnClickListener{
            startActivity<SetAvatartActivity>()
        }
    }

    fun addPermission() {
        var rxPermissions = RxPermissions(this)
        rxPermissions
            .request(Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if (granted) { // Always true pre-M
                    // I can control the camera now
                } else {
                    // Oups permission denied
                }
            }
    }

}