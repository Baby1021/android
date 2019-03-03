package com.laiyuanwen.android.baby.x5

import android.app.Activity
import android.preference.PreferenceManager
import android.webkit.JavascriptInterface

/**
 * Created by laiyuanwen on 2019-02-26.
 */
class BabyBridge(private val activity: Activity) {

    @JavascriptInterface
    fun getUserId(): String =
            PreferenceManager.getDefaultSharedPreferences(activity).getString("userId", "") ?: ""

}