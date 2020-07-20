package com.laiyuanwen.android.baby.platform.oss

import android.content.Context
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.OSSClient
import com.laiyuanwen.android.baby.BabyApplication

/**
 * Created by laiyuanwen on 2020/7/21.
 */
object BabyOSSClient {

    lateinit var oss: OSSClient

    fun init(applicationContext: Context) {
        val endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        val credentialProvider = BabyOSSCredentialsProvider(applicationContext, "http://192.168.0.103:7001/upload")

        // 配置类如果不设置，会有默认配置。
        val conf = ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
//        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

        oss = OSSClient(BabyApplication.getApplicationContext(), endpoint, credentialProvider, conf);
    }
}