package com.laiyuanwen.android.baby.platform

import android.Manifest
import android.app.Activity
import android.app.Application
import android.util.Log
import com.laiyuanwen.android.baby.Common
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception

/**
 * Created by laiyuanwen on 2019-02-08.
 */
object UpdateChecker : EasyPermissions.PermissionCallbacks {
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        // ignore 这个方法应该是不需要实现的
    }

    /**
     * 检查更新
     */
    fun scheckUpdate(activity: Activity) {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(activity, *perms)) {
//            checkUpdate()
        } else {
            EasyPermissions.requestPermissions(activity, "下载需要外部写权限哦", Common.PermissionRequestCode.RC_WRITE_EXTERNAL_STORAGE, *perms)
        }
    }

    fun checkUpdate(activity: Activity) {
        PgyUpdateManager.Builder()
                .setUpdateManagerListener(object : UpdateManagerListener {
                    override fun onUpdateAvailable(appBean: AppBean) {
                        UpdateDialog(activity, true, appBean.versionName, appBean.releaseNote)
                                .setOnDownloadListener {
                                    PgyUpdateManager.downLoadApk(appBean.downloadURL)
                                }
                                .show()
                    }

                    override fun checkUpdateFailed(p0: Exception?) {
                    }

                    override fun onNoUpdateAvailable() {
                    }
                })
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .register()
    }

}