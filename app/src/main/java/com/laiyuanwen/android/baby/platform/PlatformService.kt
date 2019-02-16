package com.laiyuanwen.android.baby.platform

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.BuildConfig
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.util.getPushToken
import com.laiyuanwen.android.baby.util.getUserId
import com.laiyuanwen.android.baby.util.savePushToken
import com.pgyersdk.crash.PgyCrashManager
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.xiaomi.MiPushRegistar


/**
 * Created by laiyuanwen on 2019-01-27.
 *
 * 平台服务,提供各种自研、第三方的初始化和工具方法等
 */
object PlatformService {

    public val APP_CHANNEL = if (BuildConfig.DEBUG) "debug" else "umeng"

    private const val BUGLY_APP_ID = "7484f50fa8"

    private const val UMENG_APP_KEY = "5c5b0ca1b465f5e9d900076d"
    private const val UMENG_MESSAGE_SECRET = "45c70be2fea946a61cac41d3870714de"

    private const val MI_PUSH_APP_ID = "2882303761517937682"
    private const val MI_PUSH_APP_KEY = "5341793737682"

    fun init(application: Application) {
        initPush(application)
        initPgyCrash()
    }

    /**
     * 友盟推送
     */
    private fun initPush(application: Application) {
        UMConfigure.init(application, UMENG_APP_KEY, APP_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, UMENG_MESSAGE_SECRET)

        val mPushAgent = PushAgent.getInstance(application)

        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                Log.i("Umeng", "注册成功：deviceToken：-------->  $deviceToken")
                savePushToken(deviceToken)
            }

            override fun onFailure(p0: String?, p1: String?) {
            }
        })
        mPushAgent.onAppStart()

        HuaWeiRegister.register(application)
        MiPushRegistar.register(application, MI_PUSH_APP_ID, MI_PUSH_APP_KEY)
    }

    /**
     * 蒲公英Crash初始化
     */
    private fun initPgyCrash() {
        if (!BuildConfig.DEBUG) {
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