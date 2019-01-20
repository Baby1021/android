package com.laiyuanwen.android.baby.bean

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by laiyuanwen on 2018/12/31.
 */

@Keep
data class Task(
        val id: Long,
        val title: String,
        val description: String,
        val done: Boolean,
//        val createTime: Long,
//        val updateTime: Long,
        val processor: User
) : Serializable