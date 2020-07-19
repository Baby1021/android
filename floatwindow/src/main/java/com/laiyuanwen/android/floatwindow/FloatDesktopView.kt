package com.laiyuanwen.android.floatwindow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

/**
 * Created by laiyuanwen on 2020/7/2.
 */
class FloatDesktopView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var lastX: Float = 0F //上一次位置的X.Y坐标
    private var lastY: Float = 0F
    private var nowX: Float = 0F  //当前移动位置的X.Y坐标
    private var nowY: Float = 0F;
    private var tranX: Float = 0F; //悬浮窗移动位置的相对值
    private var tranY: Float = 0F;
    var wm: WindowManager? = null
    var lp: WindowManager.LayoutParams? = null

    val paint = Paint()

    fun setWm(wm: WindowManager, lp: WindowManager.LayoutParams) {
        this.wm = wm
        this.lp = lp
    }

    override fun onDraw(canvas: Canvas) {
//
        paint.isAntiAlias = true
        paint.strokeWidth = 5F
        paint.color = Color.BLACK

        paint.color = Color.RED
        canvas.drawArc(200F, 100F, 800F, 500F, 20F, 140F, true, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var ret = false;
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY
                ret = true;
            }
            MotionEvent.ACTION_MOVE -> {
                nowX = event.getRawX();
                nowY = event.getRawY();
                // 计算XY坐标偏移量
                tranX = nowX - lastX;
                tranY = nowY - lastY;
                // 移动悬浮窗
                lp!!.x += tranX.toInt();
                lp!!.y += tranY.toInt();
                //更新悬浮窗位置
                wm?.updateViewLayout(this, lp);
                //记录当前坐标作为下一次计算的上一次移动的位置坐标
                lastX = nowX;
                lastY = nowY;
                ret = true
            }
        }
        return ret
    }

}