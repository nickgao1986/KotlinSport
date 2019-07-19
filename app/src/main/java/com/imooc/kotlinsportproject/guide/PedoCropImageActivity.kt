package com.imooc.kotlinsportproject.guide

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import android.view.WindowManager
import com.imooc.basemodule.base.BaseActivity
import com.imooc.basemodule.util.GlobalCont
import com.imooc.kotlinsportproject.R
import kotlinx.android.synthetic.main.pedo_cropimage.*
import java.io.File
import java.io.FileOutputStream

class PedoCropImageActivity : BaseActivity() {

    private var mBitmap: Bitmap? = null
    private lateinit var mPath:String

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initListener()
        val target = intent.data

        if (target.scheme.equals("content")) {
            //打开系统图册的回调
            val cursor = managedQuery(target, arrayOf(Media.DATA), null, null, null)
            if (cursor != null) {
                val columnIndex = cursor.getColumnIndexOrThrow(Media.DATA)
                cursor.moveToFirst()
                //mpath表示选择的那张图片的路径
                mPath = cursor.getString(columnIndex)
                cursor.close()
            }
        } else if (target.scheme == "file") {
            //打开相机的回调
            mPath = target.path
        }

        //通过BitmapFactory.decodeFile把获取图片的bitmap
        mBitmap = makeBitmap(mPath)
        mBitmap?.let {
            clip_layout.setSourceImage(mBitmap, window)
        }

    }

    /**
     * 初始化监听
     */
    fun initListener() {
        discard.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        save.setOnClickListener {
            onSaveClicked()
        }
    }

    private fun onSaveClicked() {
        val bitmap = clip_layout.bitmap
        saveImage(bitmap)
        finish()
    }

    private fun saveImage(bitmap: Bitmap?) {
        val photo = File(GlobalCont.AVATAR_PATH)
        val stream = FileOutputStream(photo)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    }

    override fun getTitleString(): Any? {
        return null
    }

    override fun getContentView(): Int {
        return R.layout.pedo_cropimage
    }

    fun makeBitmap(filePath: String?): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)

        options.inSampleSize = 3
        options.inJustDecodeBounds = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(filePath, options)
    }

    override fun onLeftButtonClick() {

    }


}
