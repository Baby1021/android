package com.laiyuanwen.android.baby.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.laiyuanwen.android.baby.LoginActivity
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.love.LoveDetailActivity

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
    intent.putExtras(bundle)
    fragment.startActivityForResult(intent, code)
}

fun toLogin(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
}
