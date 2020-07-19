package com.laiyuanwen.android.baby.ui.pages.homepage.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.sdk.android.oss.*
import com.alibaba.sdk.android.oss.common.OSSLog
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.model.GetObjectRequest
import com.amap.api.location.AMapLocationListener
import com.laiyuanwen.android.baby.BabyApplication.Companion.getApplicationContext
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.bean.response.HomeInfo
import com.laiyuanwen.android.baby.databinding.FragmentHomeBinding
import com.laiyuanwen.android.baby.repository.HomeRepository
import com.laiyuanwen.android.baby.util.location.LocationManager
import com.laiyuanwen.android.baby.util.setStatusBarColor
import com.laiyuanwen.android.baby.util.toAnniversary
import com.laiyuanwen.android.baby.util.toBill
import com.laiyuanwen.android.baby.util.toMap
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream


/**
 * Created by laiyuanwen on 2019-02-11.
 *
 * 小宝贝首页
 */
class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var handler: Handler

    private lateinit var credentialProvider: OSSCredentialProvider
    private lateinit var oss: OSS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler()

        val endpoint = "http://oss-cn-shenzhen.aliyuncs.com";

        // 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
        credentialProvider = OSSAuthCredentialsProvider("http://192.168.0.103:7001/upload")

        // 配置类如果不设置，会有默认配置。
        val conf = ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
//        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

        oss = OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
    }

    suspend fun getImage(){
        //构造下载文件请求
        //objectKey等同于objectName，表示从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg
        val get = GetObjectRequest("image-baby", "b79689cd75cf5d3-200x180.jpg")

        //设置下载进度回调
        get.setProgressListener { request, currentSize, totalSize ->
            OSSLog.logDebug("getobj_progress: $currentSize  total_size: $totalSize", false)
        }

        try {
            // 同步执行下载请求，返回结果
            val getResult = oss.getObject(get)
            Log.d("Content-Length", "" + getResult.contentLength)

            // 获取文件输入流
            val inputStream: InputStream = getResult.objectContent
            val buffer = ByteArray(2048)
            var len: Int
            while (inputStream.read(buffer).also({ len = it }) != -1) {
                // 处理下载的数据，比如图片展示或者写入文件等
            }

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

    override fun onStart() {
        super.onStart()
        setStatusBarColor(activity!!, ContextCompat.getColor(context!!, R.color.baby_white))
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = HomeRepository.getInstance().getHomeInfo()

                withContext(Dispatchers.Main) {
                    updateInfo(result)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInfo(info: HomeInfo) {
        binding.leftState.text = info.lover?.addressName
    }

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.rightStateLayout.setOnClickListener {
            context?.let { toMap(it) }
        }
        binding.leftStateLayout.setOnClickListener {
            toast("留言功能开发中")
            GlobalScope.launch {
                getImage()
            }
        }
        binding.loverImage.setOnClickListener {
            toast("图片功能开发中")
        }
        binding.billItem.setOnClickListener {
            activity?.let { toBill(it) }
        }
        binding.anniversaryItem.setOnClickListener {
            activity?.let { toAnniversary(it) }
        }
        binding.shoppingCartItem.setOnClickListener {
            toast("购物车功能开发中")
        }
        binding.depositItem.setOnClickListener {
            toast("小金库功能开发中")
        }
        binding.targetItem.setOnClickListener {
            toast("小目标功能开发中")
        }
        binding.recentPhotoItem.setOnClickListener {
            toast("宝贝近照功能开发中")
        }

        observeTime()
        observeLocation()
    }

    private fun observeLocation() {
        val cacheLocation = LocationManager.getCacheLocation()
        if (cacheLocation !== null) {
            binding.rightState.text = cacheLocation.aoiName
        } else {
            LocationManager.getLocation(AMapLocationListener {
                it?.let { binding.rightState.text = it.aoiName }
            })
        }
    }

    private fun observeTime() {
        viewModel.time.value?.apply { updateTime(this) }
        viewModel.time.observe(this, Observer { time ->
            updateTime(time)
        })
    }

    private fun updateTime(time: Time) {
        binding.loveDate.text = "${time.day}天"
        binding.loveTime.text = resources.getString(R.string.string_time, time.hour, time.minutes, time.seconds)
    }

    private fun toast(content: String) {
        Toast.makeText(context, "\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D$content", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stop()
    }

}