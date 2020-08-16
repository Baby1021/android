package com.laiyuanwen.android.glide.loader

import android.util.Log
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.common.OSSLog
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import java.io.IOException
import java.io.InputStream

/**
 * Created by laiyuanwen on 2020/7/19.
 */
class OSSDataFetcher(val model: OSSImageData) : DataFetcher<InputStream> {

    override fun getDataClass() = InputStream::class.java

    override fun cleanup() {
    }

    override fun getDataSource(): DataSource = DataSource.REMOTE

    override fun cancel() {
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {

        val key = model.url.substring(model.url.lastIndexOf('/') + 1)

        //构造下载文件请求
        //objectKey等同于objectName，表示从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
        val get = GetObjectRequest("image-baby", key)

        //设置下载进度回调
        get.setProgressListener { request, currentSize, totalSize ->
            OSSLog.logDebug("getobj_progress: $currentSize  total_size: $totalSize", false)
        }

        try {
            // 同步执行下载请求，返回结果
            val getResult = this.model.oss.getObject(get)
            Log.d("Content-Length", "" + getResult.contentLength)

            // 获取文件输入流
            val inputStream: InputStream = getResult.objectContent
//            val buffer = ByteArray(2048)
//            var len: Int
//            while (inputStream.read(buffer).also({ len = it }) != -1) {
//                // 处理下载的数据，比如图片展示或者写入文件等
//            }

            callback.onDataReady(inputStream)

            // 下载后可以查看文件元信息
            val metadata = getResult.metadata
            Log.d("ContentType", metadata.contentType)
        } catch (e: ClientException) {
            // 本地异常如网络异常等
            e.printStackTrace()
        } catch (e: ServiceException) {
            // 服务异常
            Log.e("RequestId", e.requestId)
            Log.e("ErrorCode", e.errorCode)
            Log.e("HostId", e.hostId)
            Log.e("RawMessage", e.rawMessage)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}