package com.laiyuanwen.android.baby

import android.annotation.SuppressLint
import android.content.Context
import io.flutter.app.FlutterApplication

/**
 * Created by laiyuanwen on 2018/12/31.
 */
class TeamworkApplication : FlutterApplication() {


    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var contextSingle: Context

        fun getApplicationContext(): Context = contextSingle;
    }

    override fun onCreate() {
        super.onCreate()
        contextSingle = applicationContext
    }
}