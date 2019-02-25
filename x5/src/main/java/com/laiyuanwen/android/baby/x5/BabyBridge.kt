package com.laiyuanwen.android.baby.x5

import android.app.Activity
import android.webkit.JavascriptInterface

/**
 * Created by laiyuanwen on 2019-02-26.
 */
class BabyBridge(activity: Activity) {

    @JavascriptInterface
    fun getUserId(): String {
        return "UserID"
    }
}