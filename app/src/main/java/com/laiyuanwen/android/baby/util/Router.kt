package com.laiyuanwen.android.baby.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.laiyuanwen.android.baby.LoginActivity
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.flutter.LoveDetailActivity
import com.laiyuanwen.android.baby.x5.BabyBrowserActivity

/**
 * Created by laiyuanwen on 2019-01-25.
 */

fun startLoveDetailActivity(fragment: Fragment?, love: Love?, code: Int) {
    if (fragment == null) {
        return
    }
    val intent = Intent(fragment.context, LoveDetailActivity::class.java)
    val bundle = Bundle()
    if (love != null) {
        bundle.putString("love", Provider.getGson().toJson(love))
    }
    intent.putExtra("route", "/router/love/detail")
    intent.putExtras(bundle)
    intent.action = "android.intent.action.RUN"
    fragment.startActivityForResult(intent, code)
}

fun toLogin(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
}

// 记账
fun toBill(activity: Activity) {

    val input = EditText(activity)
    val builder = AlertDialog.Builder(activity)

    input.setText("http://")

    builder.setTitle("输入环境(线上环境不需要输入)")
            .setView(input)
            .setNegativeButton("线上环境") { dialog, _ ->
                val intent = Intent(activity, BabyBrowserActivity::class.java)
                intent.putExtra(BabyBrowserActivity.URL, "http://39.108.227.137:3000/bill/index.html")
                activity.startActivity(intent)
            }
            .setPositiveButton("指定测试环境") { _, _ ->
                val intent = Intent(activity, BabyBrowserActivity::class.java)
                intent.putExtra(BabyBrowserActivity.URL, input.text.toString())
                activity.startActivity(intent)
            }
    builder.show()

}
