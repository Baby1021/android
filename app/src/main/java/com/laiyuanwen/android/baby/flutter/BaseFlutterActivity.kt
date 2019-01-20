package com.laiyuanwen.android.baby.flutter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.laiyuanwen.android.baby.Common.CHANNEL_METHOD_GET_ARGUMENT
import com.laiyuanwen.android.baby.Common.CHANNEL_PAGE_ARGUMENT
import com.laiyuanwen.android.baby.util.Provider
import com.laiyuanwen.android.baby.util.getUserId
import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


/**
 * Created by laiyuanwen on 2019/1/9.
 */
@SuppressLint("Registered")
open class BaseFlutterActivity : FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
        MethodChannel(flutterView, CHANNEL_PAGE_ARGUMENT).setMethodCallHandler { call, result ->
            if (call.method == CHANNEL_METHOD_GET_ARGUMENT) {
                result.success(toJson())
            }
        }
    }

    private fun toJson(): String {
        if (intent == null || intent.extras == null) {
            return "{}"
        }
        val bundle: Bundle = intent.extras

        bundle.putString("userId", getUserId())

        val json = JsonObject()

        bundle.keySet().forEach { key ->
            val value = bundle.get(key)
            when (value) {
                is Number -> json.addProperty(key, value)
                is Boolean -> json.addProperty(key, value)
                is String -> {
                    try {
                        json.add(key, Provider.getGson().fromJson(value, JsonObject::class.java))
                    } catch (e: JsonSyntaxException) {
                        json.addProperty(key, value)
                    }
                }
            }
        }

        Log.d("Flutter", "页面参数：$json")
        return json.toString()
    }
}