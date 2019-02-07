package com.laiyuanwen.android.baby.util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.laiyuanwen.android.baby.BuildConfig
import com.laiyuanwen.android.baby.api.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Created by laiyuanwen on 2019-01-19.
 *
 * todo inline
 * todo 抽离常量
 * todo 优化DownloadManager的用法 (下载失败、downloadId、下载进度等等）
 */

const val APK_MIME_TYPE = "application/vnd.android.package-archive"

/**
 * 检查更新
 */
fun checkUpdate(context: Context,
                update: (url: String) -> Unit,
                cancel: () -> Unit = {}) {

    CoroutineScope(Dispatchers.IO).launch {
        try {

            val result = RetrofitService.getBabyApi().checkUpdateAsync(BuildConfig.VERSION_CODE).await()

            if (result.version == null) {
                return@launch
            }

            withContext(Dispatchers.Main) {
                AlertDialog.Builder(context)
                        .setTitle(result.version.title)
                        .setMessage(result.version.changeLog)
                        .setPositiveButton("更新") { _, _ -> update(result.version.url) }
                        .setNegativeButton("取消") { _, _ -> cancel() }
                        .setOnCancelListener { cancel() }
                        .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

/**
 * 下载apk
 */
fun downloadApk(context: Context, url: String): Long {

    val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("baby.apk")
            .setDescription("下载完后请点击打开")
            .setMimeType(APK_MIME_TYPE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/baby.apk")

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    return downloadManager.enqueue(request)
}

/**
 * 安装apk
 */
fun installApk(context: Context) {
    try {
        val i = Intent(Intent.ACTION_VIEW)
        val filePath = Environment.getExternalStorageDirectory().absolutePath + "/" + Environment.DIRECTORY_DOWNLOADS + "/baby.apk"
        val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", File(filePath))

        i.setDataAndType(uri, APK_MIME_TYPE)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val resInfoList = context.packageManager.queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(i)

    } catch (e: Exception) {
        Log.e("baby", "安装失败")
        e.printStackTrace()
    }
}
