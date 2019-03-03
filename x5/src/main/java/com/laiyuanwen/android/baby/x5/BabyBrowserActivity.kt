package com.laiyuanwen.android.baby.x5

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.tencent.smtt.sdk.CookieSyncManager
import com.tencent.smtt.sdk.WebSettings
import kotlinx.android.synthetic.main.activity_browser_x5.*

/**
 * Created by laiyuanwen on 2019-02-25.
 */
class BabyBrowserActivity : Activity() {

    companion object {
        const val URL = "url"
    }

    private val ERROR_URL = ""
    private val EMPTY_URL = ""

    private lateinit var url: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser_x5)

        // webView的设置，没有研究过，直接抄来的
        val webSetting = x5_web_view.settings
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(false)
        webSetting.setAppCacheEnabled(true)
        webSetting.domStorageEnabled = true
        webSetting.javaScriptEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        webSetting.setAppCachePath(getDir("appcache", 0).path)
        webSetting.databasePath = getDir("databases", 0).path
        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0).path)
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND

        x5_web_view.loadUrl(getParamUrl())
        x5_web_view.addJavascriptInterface(BabyBridge(this), "BabyBridge")

        CookieSyncManager.createInstance(this)
        CookieSyncManager.getInstance().sync()
    }

    private fun getParamUrl(): String {
        if (intent == null) {
            return ERROR_URL
        }
        return intent.getStringExtra(URL) ?: ERROR_URL
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent == null || x5_web_view == null || intent.data == null)
            return
        x5_web_view.loadUrl(intent.data!!.toString())
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return if (x5_web_view != null && x5_web_view.canGoBack()) {
                x5_web_view.goBack()
                true
            } else {
                super.onKeyDown(keyCode, event)
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}