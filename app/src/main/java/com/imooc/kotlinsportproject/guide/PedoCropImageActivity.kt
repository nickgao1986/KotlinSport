package com.imooc.kotlinsportproject.guide

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import com.imooc.basemodule.base.BaseActivity
import com.imooc.basemodule.util.GlobalCont.Companion.AVATAR_PATH
import com.imooc.basemodule.util.GlobalCont.Companion.AVATAR_PATH_URI
import com.imooc.kotlinsportproject.R
import kotlinx.android.synthetic.main.pedo_cropimage.*
import java.io.File
import java.io.FileOutputStream

class PedoCropImageActivity:BaseActivity() {

    private lateinit var mPath:String
    private var mBitmap:Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        var target = intent.data
        initListener()
        if(target.scheme.equals("content")) {
            var cursor = managedQuery(target,arrayOf(MediaStore.Images.Media.DATA),
                null,null,null)
            cursor?.run {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                mPath = cursor.getString(columnIndex)
                cursor.close()
            }
        }else if(target.scheme.equals("file")){
            mPath = target.path
        }
        mBitmap = makeBitmap()
        mBitmap?.let {
            clip_layout.setSourceImage(mBitmap,window)
        }

    }

    fun makeBitmap():Bitmap? {
        var options = BitmapFactory.Options()
        options.inSampleSize = 3
        options.inJustDecodeBounds = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(mPath,options)
    }

    fun initListener() {
        discard.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        save.setOnClickListener{
            val bitmap = clip_layout.bitmap
            saveBitmap(bitmap)
            finish()
        }
    }

    fun saveBitmap(bitmap:Bitmap?) {
        var photo = File(AVATAR_PATH)
        var stream = FileOutputStream(photo)
        bitmap?.compress(Bitmap.CompressFormat.JPEG,100,stream)
    }


    override fun getTitleString(): Any? {
        return null
    }

    override fun getContentView(): Int {
        return R.layout.pedo_cropimage
    }

    override fun onLeftButtonClick() {
    }
}