package com.laiyuanwen.android.baby.repository

import com.laiyuanwen.android.baby.user.User

/**
 * Created by laiyuanwen on 2018/12/31.
 */

data class Task(
        val id: Long,
        val title: String,
        val description: String,
        val done: Boolean,
        val createTime: Long,
        val processor: User
)