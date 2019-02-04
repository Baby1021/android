package com.laiyuanwen.android.baby.bean

import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-02-04.
 */
data class LoveComment(
        val id: Int,
        val userId: String,
        val content: String,
        val loveId: Int,
        val createTime: Long,
        val user: User
) : Serializable