package com.imooc.kotlinsportproject.guide

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.imooc.basemodule.base.BaseActivity
import com.imooc.basemodule.util.GlobalCont
import com.imooc.basemodule.util.Util
import com.imooc.basemodule.util.ext.onClick
import com.imooc.kotlinsportproject.BuildConfig
import com.imooc.kotlinsportproject.R
import kotlinx.android.synthetic.main.pedo_set_avatar_layout.*
import org.jetbrains.anko.startActivity
import java.io.File


class SetAvatartActivity:BaseActivity() {

    var mFilePath = ""
    companion object {
        val PHOTO_PICKED = 1
        val PHOTO_TAKE_PIC = 2
        val CROP_PHOTO_FINISHED = 3
    }
    override fun getTitleString(): Any? {
        return ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iv_set_avatar.setOnClickListener{
            showDialog()
        }

        btn.onClick{
            startActivity<SetBirthDayActivity>()
        }
    }

    fun showDialog() {
        AlertView("上传头像", null, "取消", null,
            arrayOf("从相册中选择", "拍照"),
            this, AlertView.Style.ActionSheet, OnItemClickListener { o, position ->
                when(position) {
                    0 -> pickPic()
                    1 -> takePhoto()
                }
            }).show()

    }

    fun createTempFile() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            var sdcardPath = Environment.getExternalStorageDirectory().getPath()
            Util.initDataDirectory(sdcardPath+"/images/")
            mFilePath = sdcardPath + "/images/temp.png"
        }
    }
    /**
     * 拍照
     */
    fun takePhoto() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createTempFile()
        if(intent.resolveActivity(this.packageManager) != null) {

            var uri = FileProvider.getUriForFile(
                this,BuildConfig.APPLICATION_ID+".fileprovider",
                File(mFilePath)
            )

            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
            this.startActivityForResult(intent,PHOTO_TAKE_PIC)
        }
    }
    /**
     * 从相册里选图片
     */
    fun pickPic() {
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent,PHOTO_PICKED)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PHOTO_PICKED && resultCode == Activity.RESULT_OK) {
            var intent = Intent(this,PedoCropImageActivity::class.java)
            intent.type = "image/*"
            intent.data = data?.data
            startActivityForResult(intent,CROP_PHOTO_FINISHED)
        }else if(requestCode == PHOTO_TAKE_PIC && resultCode == Activity.RESULT_OK) {
            var intent = Intent(this,PedoCropImageActivity::class.java)
            intent.data = Uri.fromFile(File(mFilePath))
            startActivityForResult(intent,CROP_PHOTO_FINISHED)
        }else if(requestCode == CROP_PHOTO_FINISHED) {
            var imagePipeLine = Fresco.getImagePipeline()
            imagePipeLine.evictFromCache(Uri.parse(GlobalCont.AVATAR_PATH_URI))

            iv_set_avatar.setImageURI(GlobalCont.AVATAR_PATH_URI)

            UploadQiniu.uploadToQiniu(GlobalCont.AVATAR_PATH)
        }
    }

    override fun getContentView(): Int {
        return R.layout.pedo_set_avatar_layout
    }

    override fun onLeftButtonClick() {
    }
}