package com.laiyuanwen.android.baby.util

import android.content.Context
import com.laiyuanwen.android.baby.BuildConfig
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport

/**
 * Created by laiyuanwen on 2019-01-27.
 */
object BuglyCenter {

    fun init(context: Context) {
        val strategy = CrashReport.UserStrategy(context)
        strategy.appChannel = if (BuildConfig.DEBUG) "debug" else "Umeng"
        Bugly.init(context, "7484f50fa8", BuildConfig.DEBUG, strategy)
    }

}