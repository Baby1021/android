package com.laiyuanwen.android.baby.x5

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class X5WebView : WebView {
    internal var title: TextView? = null
    private val client = object : WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(webView: WebView?, s: String?) {
            super.onPageFinished(webView, s)
            // Java调用Js
//            val call = "javascript:js(\"" + "赖远文" + "\")"
//            webView!!.loadUrl(call)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    constructor(arg0: Context, arg1: AttributeSet?) : super(arg0, arg1) {
        this.webViewClient = client
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings()
        this.view.isClickable = true
    }

    private fun initWebViewSettings() {
        val webSetting = this.settings
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE

        //禁用放缩
        webSetting.displayZoomControls = false
        webSetting.builtInZoomControls = false

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    constructor(arg0: Context) : super(arg0) {
        setBackgroundColor(85621)
    }

}
