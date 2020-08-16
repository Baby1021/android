package com.laiyuanwen.android.baby.platform.oss

import android.content.Context
import android.util.Log
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import com.laiyuanwen.android.baby.BabyApplication
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.extension.md5
import java.io.File

/**
 * Created by laiyuanwen on 2020/7/21.
 */
object BabyOSSClient {

    lateinit var oss: OSSClient
    private const val OSS_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com"

    fun init(applicationContext: Context) {
        val credentialProvider = BabyOSSCredentialsProvider(applicationContext, "${RetrofitService.HOST}/upload")

        // 配置类如果不设置，会有默认配置。
        val conf = ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
//        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

        oss = OSSClient(BabyApplication.getApplicationContext(), OSS_ENDPOINT, credentialProvider, conf);
    }

    suspend fun updateImage(path: String): String {
        val key = File(path).md5() + path.substring(path.lastIndexOf('.'))
        val put: PutObjectRequest? = PutObjectRequest("image-baby", key, path)
        try {
            val putResult: PutObjectResult = oss.putObject(put)
            Log.d("PutObject", "UploadSuccess")
            Log.d("ETag", putResult.getETag())
            Log.d("RequestId", putResult.getRequestId())
        } catch (e: ClientException) {
            // 本地异常，如网络异常等。
            e.printStackTrace()
        } catch (e: ServiceException) {
            // 服务异常。
            Log.e("RequestId", e.getRequestId())
            Log.e("ErrorCode", e.getErrorCode())
            Log.e("HostId", e.getHostId())
            Log.e("RawMessage", e.getRawMessage())
        }

        return "$OSS_ENDPOINT/$key"
    }
}