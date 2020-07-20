package com.laiyuanwen.android.baby.platform

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.BabyApplication.Companion.getApplicationContext
import com.laiyuanwen.android.baby.BuildConfig
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.platform.oss.BabyOSSClient
import com.laiyuanwen.android.baby.util.getPushToken
import com.laiyuanwen.android.baby.util.getUserId
import com.pgyersdk.crash.PgyCrashManager
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import com.tencent.smtt.sdk.QbSdk
import initPush
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Created by laiyuanwen on 2019-01-27.
 *
 * 平台服务,提供各种自研、第三方的初始化和工具方法等
 */
object PlatformService {

    val APP_CHANNEL = if (BuildConfig.DEBUG) "debug" else "umeng"

    const val BUGLY_APP_ID = "7484f50fa8"

    const val UMENG_APP_KEY = "5c5b0ca1b465f5e9d900076d"
    const val UMENG_MESSAGE_SECRET = "45c70be2fea946a61cac41d3870714de"

    const val MI_PUSH_APP_ID = "2882303761517937682"
    const val MI_PUSH_APP_KEY = "5341793737682"

    fun init(application: Application) {
        initPush(application)
        initPgyCrash()
        initX5(application)
        CoroutineScope(Dispatchers.IO).launch {
            BabyOSSClient.init(getApplicationContext())
        }
    }

    private fun initX5(application: Application) {
        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(application, cb)
    }

    /**
     * 蒲公英Crash初始化
     */
    private fun initPgyCrash() {
        if (BuildConfig.DEBUG) {
            return
        }
        PgyCrashManager.register()
    }

    /**
     * 检查更新
     */
    fun checkUpdate(context: Context, showResult: Boolean) {
        PgyUpdateManager.Builder()
                .setUpdateManagerListener(object : UpdateManagerListener {
                    override fun onUpdateAvailable(appBean: AppBean) {
                        UpdateDialog(context, true, appBean.versionName, appBean.releaseNote)
                                .setOnDownloadListener {
                                    PgyUpdateManager.downLoadApk(appBean.downloadURL)
                                }
                                .show()
                    }

                    override fun checkUpdateFailed(p0: Exception?) {
                    }

                    override fun onNoUpdateAvailable() {
                        if (showResult) {
                            Toast.makeText(context, "已经是最新版本了", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .register()
    }

    /**
     * 上传PushToken
     */
    fun updatePushToken() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val json = JsonObject()
                json.addProperty("userId", getUserId())
                json.addProperty("pushToken", getPushToken())
                RetrofitService.getBabyApi().uploadPushToken(json).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}