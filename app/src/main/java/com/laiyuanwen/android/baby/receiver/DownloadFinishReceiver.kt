package com.laiyuanwen.android.baby.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.laiyuanwen.android.baby.util.installApk

/**
 * Created by laiyuanwen on 2019-01-19.
 *
 * 新版本apk下载完成的Receiver
 *
 */
class DownloadFinishReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: ""
        if (action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            installApk(context)
        }
    }
}