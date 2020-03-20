package com.laiyuanwen.android.baby.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Toast

/**
 * Created by laiyuanwen on 2019-02-16.
 */

fun setStatusBarColor(activity: Activity, statusColor: Int) {
    val window = activity.window
    //取消状态栏透明
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    //添加Flag把状态栏设为可绘制模式
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    //设置状态栏颜色
    window.statusBarColor = statusColor
    //设置系统状态栏处于可见状态
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    //让view不根据系统窗口来调整自己的布局
//        val mContentView = window.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
//        val mChildView = mContentView.getChildAt(0)
//        if (mChildView != null) {
//            mChildView.fitsSystemWindows = false
//            ViewCompat.requestApplyInsets(mChildView)
//        }
//    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

}

fun toast(context: Context?, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}