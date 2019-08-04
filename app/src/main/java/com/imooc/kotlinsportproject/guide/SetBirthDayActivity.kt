package com.imooc.kotlinsportproject.guide

import android.graphics.Typeface
import android.os.Bundle
import com.fourmob.datetimepicker.date.DatePickerDialogUtil
import com.imooc.basemodule.base.BaseActivity
import com.imooc.basemodule.util.CalendarUtil
import com.imooc.basemodule.util.ext.onClick
import com.imooc.kotlinsportproject.R
import kotlinx.android.synthetic.main.pedo_welcome_set_birth_layout.*
import org.jetbrains.anko.startActivity
import java.util.*

class SetBirthDayActivity : BaseActivity() {

    private lateinit var mDatePicker: DatePickerDialogUtil
    private var mUserBirthday: Long = 0

    override fun getTitleString(): Any {
        return ""
    }

    override fun getContentView(): Int {
        return R.layout.pedo_welcome_set_birth_layout
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gender.typeface = Typeface.createFromAsset(mContext.assets, "fonts/PingFang-SC-Semibold.ttf")
        set_birth_text.typeface = Typeface.createFromAsset(mContext.assets, "fonts/PingFang-SC-Semibold.ttf")

        initListener()

    }

    fun initListener() {
        btn.onClick {
            startActivity<SetHeightAndWeightActivity>()

        }

        gender1.onClick {
            gender1.isSelected = true
            gender2.isSelected = false
        }


        gender2.onClick {
            gender1.isSelected = false
            gender2.isSelected = true
        }


        set_birth.onClick { showSetBirth() }
    }

    private fun showSetBirth() {
        val minCalender = Calendar.getInstance(Locale.CHINA)
        val currentYear = minCalender.get(Calendar.YEAR)
        //2019-20=1999
        minCalender.set(currentYear - 20, 0, 1)


        val maxCalender = Calendar.getInstance(Locale.CHINA)
        maxCalender.set(currentYear - 12, 0, 1)

        //2019-18=2001
        val selectCalendar = Calendar.getInstance(Locale.CHINA)
        selectCalendar.set(currentYear - 18, 0, 1)

        mDatePicker = DatePickerDialogUtil(
            this,
            selectCalendar,
            DatePickerDialogUtil.ShowState.SHOW_DATE_AND_TEXT_ONLY,
            maxCalender,
            minCalender
        )
        mDatePicker.listener = object : DatePickerDialogUtil.OnDateChangeListener {
            override fun onDateChange(calendar: Calendar) {}

            override fun onConfirm(calendar: Calendar) {
                mUserBirthday = calendar.timeInMillis
                set_birth.text = CalendarUtil.format4(mUserBirthday)
            }
        }
        mDatePicker.calendar = selectCalendar
        mDatePicker.show()
    }


}
