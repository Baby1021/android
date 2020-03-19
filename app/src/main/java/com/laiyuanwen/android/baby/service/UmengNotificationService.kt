package com.laiyuanwen.android.baby.service

import android.content.Context
import android.content.Intent
import android.util.Log
import com.umeng.message.UmengMessageService
import org.android.agoo.common.AgooConstants


/**
 * Created by laiyuanwen on 2020/3/19.
 */
class UmengNotificationService : UmengMessageService() {
    override fun onMessage(context: Context?, intent: Intent) {
        val message = intent.getStringExtra(AgooConstants.MESSAGE_BODY)
        //处理消息内容
        Log.d("laiyuanwen_debug","消息回调：${message}")
    }
}