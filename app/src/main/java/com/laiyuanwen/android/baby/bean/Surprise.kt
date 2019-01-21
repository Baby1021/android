package com.laiyuanwen.android.baby.bean

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-01-21.
 */

@Keep
data class Surprise(
        val id: Long,
        val type: String,
        val content: String,
        val startTime: Long,
        val endTime: Long,
        val createTime: Long
) : Serializable

@Keep
data class SurpriseResponse(
        val code: Int,
        val message: String,
        val data: Surprise?
) : Serializable