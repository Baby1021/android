package com.laiyuanwen.android.baby

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.laiyuanwen.android.baby.receiver.DownloadFinishReceiver
import com.laiyuanwen.android.baby.surprise.TextSurpriseDialogFragment
import com.laiyuanwen.android.baby.util.checkUpdate
import com.laiyuanwen.android.baby.util.downloadApk

/**
 * todo 记得注销广播
 */
class MainActivity : AppCompatActivity() {

    private val downloadFinishReceiver = DownloadFinishReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (hasNotification()) {
            TextSurpriseDialogFragment().show(supportFragmentManager, "")
        }

        checkUpdate(this, { url ->
            downloadApk(this@MainActivity, url)
            registerReceiver(downloadFinishReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        })
    }

    private fun hasNotification(): Boolean {
        return true
    }
}
