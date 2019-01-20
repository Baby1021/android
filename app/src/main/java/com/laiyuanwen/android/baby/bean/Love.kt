package com.laiyuanwen.android.baby.bean

import com.laiyuanwen.android.baby.user.User
import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-01-20.
 *
 * Love数据结构
 */
data class Love(
        val id: Long,
        val content: String,
        val user: User,
        val createTime: Long
) : Serializable