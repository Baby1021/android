package com.laiyuanwen.android.baby

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.laiyuanwen.android.baby.platform.PlatformService


/**
 * Created by laiyuanwen on 2018/12/31.
 */
class BabyApplication : Application() {


    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var contextSingle: Context

        fun getApplicationContext(): Context = contextSingle;
    }

    override fun onCreate() {
        super.onCreate()
        contextSingle = applicationContext

        PlatformService.init(this)
    }
}