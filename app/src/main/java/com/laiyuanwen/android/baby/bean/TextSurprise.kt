package com.laiyuanwen.android.baby.bean

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-01-20.
 */

@Keep
data class TextSurprise(
        val id: Long,
        val title: String,
        val content: String,
        val imageUrl: String
) : Serializable