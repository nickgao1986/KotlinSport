package com.imooc.sport.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.imooc.basemodule.base.Util
import com.imooc.kotlinsportproject.R
import com.imooc.kotlinsportproject.welcome.WelcomeActivity


class CountDownView @JvmOverloads constructor(
    private val mContext: Context//上下文
    , attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(mContext, attrs, defStyleAttr) {
    private lateinit var mPaintBackGround: Paint//背景画笔
    private lateinit var mPaintArc: Paint//圆弧画笔
    private lateinit var mPaintText: Paint//文字画笔
    private val mPaintArcWidth: Float = Util.dp2px(mContext, 2f).toFloat()//最外层圆弧的宽度
    private val mCircleRadius: Int//圆圈的半径
    private var mPaintArcColor = mContext.resources.getColor(R.color.black_at)//初始值
    private var mLoadingTime: Int = 3//时间，单位秒
    private var mTextColor = mContext.resources.getColor(R.color.black_at)//字体颜色
    private val mTextSize: Int = Util.dp2px(mContext, 9f)//字体大小
    private var startAngle: Float =  -180f//开始角度
    private var mmSweepAngleStart: Float = 0f//起点
    private var mmSweepAngleEnd: Float = 360f//终点
    private var mSweepAngle: Float = 0.toFloat()//扫过的角度
    private var mText = ""//要绘制的文字
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    init {
        val array = mContext.obtainStyledAttributes(attrs,R.styleable.CountDownView)
        mCircleRadius = array.getDimension(R.styleable.CountDownView_radius,
            Util.dp2px(mContext,24f).toFloat()).toInt()
        array.recycle()
        init()
    }

    private fun init() {
        mPaintBackGround = Paint()
        mPaintBackGround.style = Paint.Style.FILL
        mPaintBackGround.isAntiAlias = true
        mPaintBackGround.color = mContext.resources.getColor(R.color.color_4C000000)

        mPaintArc = Paint()
        mPaintArc.style = Paint.Style.STROKE
        mPaintArc.isAntiAlias = true
        mPaintArc.color = mPaintArcColor
        mPaintArc.strokeWidth = mPaintArcWidth

        mPaintText = Paint()
        mPaintText.style = Paint.Style.STROKE
        mPaintText.isAntiAlias = true
        mPaintText.color = mTextColor
        mPaintText.textSize = mTextSize.toFloat()

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mCircleRadius*2,mCircleRadius*2)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle((mWidth/2).toFloat(),
            (mWidth/2).toFloat(),(mWidth/2-mPaintArcWidth).toFloat(),mPaintBackGround)


        val rectF = RectF(mPaintArcWidth/2,mPaintArcWidth/2,
            mWidth - mPaintArcWidth/2,mHeight-mPaintArcWidth/2)
        canvas.drawArc(rectF,startAngle,mSweepAngle,false,mPaintArc)

        val mTextWidth = mPaintText.measureText(mText,0,mText.length)
        val x = mWidth/2-mTextWidth/2
        val fontMetricsInt = mPaintText.fontMetricsInt
        val dy = (fontMetricsInt.bottom+fontMetricsInt.top)/2
        val baseLine = (mHeight/2 - dy).toFloat()
        canvas.drawText(mText,x,baseLine,mPaintText)

    }

    fun start() {
        val animator = ValueAnimator.ofFloat(mmSweepAngleStart,mmSweepAngleEnd)

        animator.addUpdateListener {
            valueAnimator ->
            mSweepAngle = valueAnimator.animatedValue as Float
            invalidate()
        }

        val animatorText = ValueAnimator.ofInt(mLoadingTime,0)

        animatorText.addUpdateListener {
                valueAnimator ->
            mText = "${valueAnimator.animatedValue}秒"
        }

        val set = AnimatorSet()
        set.setDuration((mLoadingTime*1000).toLong()).playTogether(animator,animatorText)
        set.start()

        set.addListener(object :AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                clearAnimation()
            }
        })

    }

}
