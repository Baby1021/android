package com.laiyuanwen.android.baby

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.Common.PermissionRequestCode.RC_WRITE_EXTERNAL_STORAGE
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.platform.PlatformService
import com.laiyuanwen.android.baby.util.getPushToken
import com.laiyuanwen.android.baby.util.getUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PlatformService.updatePushToken()
        checkUpdate()
    }

    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE)
    fun checkUpdate() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            PlatformService.checkUpdate(this, false)
        } else {
            EasyPermissions.requestPermissions(this, "缓存数据和下载需要外部写权限哦", Common.PermissionRequestCode.RC_WRITE_EXTERNAL_STORAGE, *perms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
