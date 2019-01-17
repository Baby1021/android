package com.laiyuanwen.android.baby.user

import java.io.Serializable

/**
 * Created by laiyuanwen on 2018/12/31.
 */

data class User(
        val userId: String,
        val avatar: String,
        val name: String
) : Serializable