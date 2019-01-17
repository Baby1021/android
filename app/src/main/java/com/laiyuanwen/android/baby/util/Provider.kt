package com.laiyuanwen.android.baby.util

import com.google.gson.Gson

/**
 * Created by laiyuanwen on 2019/1/9.
 */
object Provider {

    @Volatile
    private var mGson: Gson? = null

    fun getGson() = mGson ?: synchronized(this) {
        mGson ?: Gson().also { mGson = it }
    }
}