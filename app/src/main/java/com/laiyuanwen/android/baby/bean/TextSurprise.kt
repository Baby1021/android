package com.laiyuanwen.android.baby.bean

import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-01-20.
 */

data class TextSurprise(
        val id: Long,
        val title: String,
        val content: String,
        val imageUrl: String
) : Serializable