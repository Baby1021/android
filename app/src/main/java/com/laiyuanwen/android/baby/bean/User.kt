package com.laiyuanwen.android.baby.bean

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by laiyuanwen on 2018/12/31.
 */

@Keep
data class User(
        val userId: String,
        val avatar: String,
        val name: String
) : Serializable