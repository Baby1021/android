package com.laiyuanwen.android.baby.push

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.laiyuanwen.android.baby.LoginActivity
import com.laiyuanwen.android.baby.ui.pages.homepage.MainActivity
import com.laiyuanwen.android.baby.util.isLogin
import com.umeng.message.UmengNotifyClickActivity
import org.android.agoo.common.AgooConstants

/**
 * Created by laiyuanwen on 2019-02-07.
 */
class PushActivity : UmengNotifyClickActivity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        if (isLogin(this)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

    override fun onMessage(intent: Intent) {
        super.onMessage(intent) //此方法必须调用，否则无法统计打开数
        val body = intent.getStringExtra(AgooConstants.MESSAGE_BODY)
        Log.i(TAG, body)
    }

    companion object {
        private val TAG = PushActivity::class.java.name
    }
}