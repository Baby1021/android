package com.laiyuanwen.android.glide.loader

import com.alibaba.sdk.android.oss.OSS
import com.alibaba.sdk.android.oss.OSSClient

/**
 * Created by laiyuanwen on 2020/7/19.
 */
data class OSSImageData(
        val oss: OSSClient,
        val url: String
)