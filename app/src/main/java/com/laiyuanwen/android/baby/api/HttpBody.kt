package com.laiyuanwen.android.baby.api

import androidx.annotation.Keep

/**
 * Created by laiyuanwen on 2019-02-04.
 */
@Keep
data class HttpBody<T>(
        val message: String,
        val code: Int,
        val data: T?)