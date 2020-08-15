package com.laiyuanwen.android.baby.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.laiyuanwen.android.baby.Common
import com.laiyuanwen.android.baby.LoginActivity
import com.laiyuanwen.android.baby.repository.SettingRepository
import com.laiyuanwen.android.baby.ui.pages.love.PublishLoveActivity
import com.laiyuanwen.android.baby.ui.pages.map.MapActivity
import com.laiyuanwen.android.baby.x5.BabyBrowserActivity

/**
 * Created by laiyuanwen on 2019-01-25.
 */

fun toPublishLove(fragment: Fragment?, code: Int) {
    if (fragment == null) {
        return
    }
    val intent = Intent(fragment.context, PublishLoveActivity::class.java)
    fragment.startActivityForResult(intent, code)
}

fun toLogin(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
}

fun toMap(context: Context) {
    val intent = Intent(context, MapActivity::class.java)
    context.startActivity(intent)
}

// 记账
fun toBill(activity: Activity) {

    val setting = SettingRepository.getInstance()

    if (!setting.isDebug()) {
        val intent = Intent(activity, BabyBrowserActivity::class.java)
        intent.putExtra(BabyBrowserActivity.URL, "http://39.108.227.137:3030/index.html")
        activity.startActivity(intent)
    } else {
        val input = EditText(activity)
        val builder = AlertDialog.Builder(activity)

        input.setText(setting.getDebugHost())

        builder.setTitle("输入环境")
                .setView(input)
                .setPositiveButton("指定测试环境") { _, _ ->
                    val intent = Intent(activity, BabyBrowserActivity::class.java)
                    val server = input.text.toString()
                    SettingRepository.getInstance().saveDebugHost(server)
                    intent.putExtra(BabyBrowserActivity.URL, server)
                    activity.startActivity(intent)
                }
        builder.show()
    }
}

/**
 * 纪念日
 */
fun toAnniversary(activity: Activity) {
    val uri = Uri.parse("baby://com.laiyuanwen.baby/anniversary")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    activity.startActivityForResult(intent, -1)
}

fun toSetting(activity: Activity) {
    val uri = Uri.parse("baby://com.laiyuanwen.baby/settings")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    activity.startActivityForResult(intent, -1)
}

fun toImageSelect(fragment: Fragment) {
    val intent = Intent(Intent.ACTION_PICK);
    intent.type = "image/*";
    fragment.startActivityForResult(intent, Common.RequestCode.SELECT_IMAGE);
}
