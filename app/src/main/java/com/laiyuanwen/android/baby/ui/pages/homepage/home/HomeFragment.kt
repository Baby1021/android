package com.laiyuanwen.android.baby.ui.pages.homepage.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.amap.api.location.AMapLocationListener
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.BabyApplication.Companion.getApplicationContext
import com.laiyuanwen.android.baby.GlideApp
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.bean.response.HomeInfo
import com.laiyuanwen.android.baby.databinding.FragmentHomeBinding
import com.laiyuanwen.android.baby.platform.oss.BabyOSSClient
import com.laiyuanwen.android.baby.repository.HomeRepository
import com.laiyuanwen.android.baby.util.location.LocationManager
import com.laiyuanwen.android.baby.util.setStatusBarColor
import com.laiyuanwen.android.baby.util.toAnniversary
import com.laiyuanwen.android.baby.util.toBill
import com.laiyuanwen.android.baby.util.toMap
import com.laiyuanwen.android.glide.loader.OSSImageData
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*


/**
 * Created by laiyuanwen on 2019-02-11.
 *
 * 小宝贝首页
 */
class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler()
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
                withContext(Dispatchers.Main) {
                    GlideApp.with(lover_image)
                            .load(OSSImageData(BabyOSSClient.oss, "https://image-baby.oss-cn-shenzhen.aliyuncs.com/b79689cd75cf5d3-200x180.jpg"))
                            .into(lover_image)
                }
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