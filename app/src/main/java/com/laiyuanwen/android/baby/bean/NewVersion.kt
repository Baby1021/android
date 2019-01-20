package com.laiyuanwen.android.baby.bean

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by laiyuanwen on 2019-01-19.
 *
 * 新版本
 */
@Keep
data class NewVersion(
        val versionCode: Int,
        val versionName: String,    // 版本名字
        val url: String,            // 下载链接
        val title: String,          // 更新窗口标题
        val changeLog: String,      // 更新信息
        val force: Boolean          // 是否强制更新
) : Serializable

@Keep
data class NewVersionResult(val version: NewVersion?)