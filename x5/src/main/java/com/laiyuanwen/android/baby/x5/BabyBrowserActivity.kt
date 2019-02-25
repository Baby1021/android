package com.laiyuanwen.android.baby.x5

import android.app.Activity
import android.os.Bundle
import android.webkit.JavascriptInterface
import com.laiyuanwen.android.baby.x5.demo.WebViewJavaScriptFunction
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser_x5)

        x5_web_view.loadUrl(getParamUrl())
        x5_web_view.addJavascriptInterface(BabyBridge(this), "BabyBridge")
    }

    private fun getParamUrl(): String {
        if (intent == null) {
            return ERROR_URL
        }
        return intent.getStringExtra(URL) ?: ERROR_URL
    }
}