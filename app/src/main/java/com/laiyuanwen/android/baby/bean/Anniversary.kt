package com.laiyuanwen.android.baby.bean

import androidx.annotation.Keep

/**
 * Created by laiyuanwen on 2019-06-07.
 */
@Keep
data class Anniversary(
        val id: Long,       // 纪念日id
        val name: String,   // 纪念日名字
        val time: Long // 纪念日开始时间
)