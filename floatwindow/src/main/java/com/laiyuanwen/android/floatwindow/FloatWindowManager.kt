package com.laiyuanwen.android.floatwindow

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager

/**
 * Created by laiyuanwen on 2020/7/2.
 *
 * 控制DesktopView的初始化和展示逻辑
 */
object FloatWindowManager {

    /**
     * 显示浮动窗口
     */
    fun show(context: Context) {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val lp = WindowManager.LayoutParams()
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY or // 接受事件
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY // 悬浮在系统上


        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按,不设置这个flag的话，home页的划屏会有问题
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //悬浮窗默认显示的位置
        lp.gravity = Gravity.LEFT or Gravity.TOP;
        //显示位置与指定位置的相对位置差
        lp.x = 0;
        lp.y = 0;
        //悬浮窗的宽高
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        lp.format = PixelFormat.TRANSPARENT;
        val view = FloatDesktopView(context)

        view.setWm(wm,lp)

        wm.addView(view, lp)
    }

    /**
     * 隐藏浮动窗口
     */
    fun hide() {}
}