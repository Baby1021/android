package com.laiyuanwen.android.baby.bean

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-01-20.
 *
 * Love数据结构
 */
@Keep
data class Love(
        val id: Long,
        val content: String,
        val user: User,
        val createTime: Long,
        val images: List<String>?
) : Serializable