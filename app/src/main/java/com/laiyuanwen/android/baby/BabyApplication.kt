package com.laiyuanwen.android.baby

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import io.flutter.app.FlutterApplication
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.xiaomi.MiPushRegistar


/**
 * Created by laiyuanwen on 2018/12/31.
 */
class BabyApplication : FlutterApplication() {


    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var contextSingle: Context

        fun getApplicationContext(): Context = contextSingle;
    }

    override fun onCreate() {
        super.onCreate()
        contextSingle = applicationContext

        initUmeng()
        HuaWeiRegister.register(this)
        MiPushRegistar.register(this, "2882303761517937682", "5341793737682")
    }

    private fun initUmeng() {
        UMConfigure.init(this,
                "5c5b0ca1b465f5e9d900076d",
                if (BuildConfig.DEBUG) "debug" else "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE,
                "45c70be2fea946a61cac41d3870714de"
        )

        val TAG = "Umeng测试"
        val mPushAgent = PushAgent.getInstance(this)
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                Log.e(TAG, "注册失败：-------->  s:$s,s1:$s1")
            }
        })

        PushAgent.getInstance(this).onAppStart()
    }
}