package com.laiyuanwen.android.baby.flutter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.laiyuanwen.android.baby.Common.ActivityRequestCode.FLUTTER_RESULT
import com.laiyuanwen.android.baby.Common.CHANNEL_METHOD_GET_ARGUMENT
import com.laiyuanwen.android.baby.Common.CHANNEL_METHOD_SET_RESULT
import com.laiyuanwen.android.baby.Common.CHANNEL_PAGE_ARGUMENT
import com.laiyuanwen.android.baby.util.Provider
import com.laiyuanwen.android.baby.util.getUserId
import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import org.json.JSONObject


/**
 * Created by laiyuanwen on 2019/1/9.
 */
@SuppressLint("Registered")
open class BaseFlutterActivity : FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
        MethodChannel(flutterView, CHANNEL_PAGE_ARGUMENT).setMethodCallHandler { call, result ->
            when (call.method) {
                CHANNEL_METHOD_GET_ARGUMENT -> {
                    result.success(toJson())
                }
                CHANNEL_METHOD_SET_RESULT -> {
                    val bundle = getBundle(call)
                    val intent = Intent()
                    if (bundle != null) {
                        intent.putExtras(bundle)
                    }
                    setResult(FLUTTER_RESULT, intent)
                    result.success("{}")
                }
            }
        }
    }

    private fun getBundle(call: MethodCall): Bundle? {

        val result = call.arguments<Any>()

        return when (result) {
            null -> Bundle()
            is Map<*, *> -> getArgumentByMap(result as Map<String, Any>)
            is JSONObject -> getArgumentByJson(result)
            else -> throw ClassCastException()
        }
    }

    private fun getArgumentByMap(map: Map<String, Any>): Bundle {
        val bundle = Bundle()
        map.forEach { entry ->
            bundle.putString(entry.key, entry.value.toString())
        }
        return bundle
    }

    private fun getArgumentByJson(json: JSONObject): Bundle {
        val bundle = Bundle()
        json.keys().forEach { key ->
            bundle.putString(key, json[key] as String)
        }
        return bundle
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