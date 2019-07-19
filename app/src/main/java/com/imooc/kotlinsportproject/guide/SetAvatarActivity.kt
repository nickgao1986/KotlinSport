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
import com.imooc.kotlinsportproject.BuildConfig
import com.imooc.kotlinsportproject.R
import kotlinx.android.synthetic.main.pedo_set_avatar_layout.*
import org.jetbrains.anko.startActivity
import java.io.File


class SetAvatarActivity : BaseActivity() {


    val CROP_PHOTO_FINISHED = 1
    var mFilePath = ""

    override fun getTitleString(): Any {
        return ""
    }

    override fun onLeftButtonClick() {
        finish()
    }

    override fun getContentView(): Int {
        return R.layout.pedo_set_avatar_layout
    }


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iv_set_avatar.setImageURI(GlobalCont.AVATAR_PATH_URI)
        btn.setOnClickListener {
        }
        iv_set_avatar.setOnClickListener {
            showDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_PICKED && resultCode == Activity.RESULT_OK) {

            val intent = Intent(this, PedoCropImageActivity::class.java)
            intent.type = "image/*"
            intent.data = data?.data
            startActivityForResult(intent, CROP_PHOTO_FINISHED)

        } else if (requestCode == PHOTO_CROP && resultCode == Activity.RESULT_OK) {

            val intent = Intent(this, PedoCropImageActivity::class.java)
            intent.data = Uri.fromFile(File(mFilePath))
            startActivityForResult(intent, CROP_PHOTO_FINISHED)

        } else if (requestCode == CROP_PHOTO_FINISHED) {


            var imagePipeline = Fresco.getImagePipeline();
            imagePipeline.evictFromCache(Uri.parse(GlobalCont.AVATAR_PATH_URI))

            iv_set_avatar.setImageURI(GlobalCont.AVATAR_PATH_URI)

        }
    }


    /*
      拍照弹框
   */
    private fun showDialog() {
        AlertView("选择图片", "", "取消", null, arrayOf("拍照", "相册"), this,
            AlertView.Style.ActionSheet, OnItemClickListener { o, position ->
                when (position) {
                    0 -> pickItem1()
                    1 -> pickItem2()
                }
            }

        ).show()

    }



    fun pickItem1() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createTempFile()
        //类似打开相机，发送图片等隐式Intent，是并不一定能够在所有的Android设备上都正常运行。
        // 例如打开相机的隐式Intent，如果系统相机应用被关闭或者不存在相机应用，
        // 又或者是相机应用的某些权限被关闭等等情况都可能导致这个隐式的Intent无法正常工作。
        // 一旦发生隐式Intent找不到合适的调用组件的情况，系统就会抛出ActivityNotFoundException的异常，
        // 如果我们的应用没有对这个异常做任何处理，那应用就会发生Crash
        if (intent.resolveActivity(this.packageManager) != null) {
            val uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                File(mFilePath)
            )
            // 主要就是这行代码，通过FileProvider获取文件的uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            this.startActivityForResult(intent, PHOTO_CROP)
        }

    }

    /*
       新建临时文件
    */
    fun createTempFile(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            var sdcardPath = Environment.getExternalStorageDirectory().getPath();
            Util.initDataDirectory(sdcardPath + "/images/");
            mFilePath = sdcardPath + "/images/temp.png"
            return
        }

    }


    fun pickItem2() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, PHOTO_PICKED)
    }



    companion object {
        private val PHOTO_CROP = 2
        private val PHOTO_PICKED = 1
    }


}
