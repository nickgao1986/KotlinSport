xmlns:app="http://schemas.android.com/apk/res-auto"


fun dp2px(context: Context, dpValue:Float):Int {
            return (dpValue*getDensity(context) + 0.5f).toInt()
}


fun getDensity(context:Context):Float{
     return context.resources.displayMetrics.density
}

 val mTextWidth = mPaintText.measureText(mText,0,mText.length)
 val x = mWidth/2-mTextWidth/2
        val fontMetricsInt = mPaintText.fontMetricsInt
        val dy = (fontMetricsInt.bottom+fontMetricsInt.top)/2
        val baseLine = (mHeight/2 - dy).toFloat()
        canvas.drawText(mText,x,baseLine,mPaintText)
