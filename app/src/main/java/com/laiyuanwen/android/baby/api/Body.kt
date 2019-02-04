package com.laiyuanwen.android.baby.api

/**
 * Created by laiyuanwen on 2019-02-04.
 */
data class Body<T>(
        val message: String,
        val code: Int,
        val data: T)