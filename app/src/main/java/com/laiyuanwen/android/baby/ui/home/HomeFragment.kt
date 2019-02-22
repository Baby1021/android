package com.laiyuanwen.android.baby.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.laiyuanwen.android.baby.Common.LOVER_START_TIME_MILLIS
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentHomeBinding
import com.laiyuanwen.android.baby.util.setStatusBarColor


/**
 * Created by laiyuanwen on 2019-02-11.
 *
 * 小宝贝首页
 */
class HomeFragment : BaseFragment() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(activity!!, ContextCompat.getColor(context!!, R.color.baby_white))
        handler = Handler()
    }

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rightStateLayout.setOnClickListener {
            toast("切换状态功能开发中")
        }
        binding.leftStateLayout.setOnClickListener {
            toast("留言功能开发中")
        }
        binding.loverImage.setOnClickListener {
            toast("图片功能开发中")
        }
        binding.billItem.setOnClickListener {
            toast("记账功能开发中")
        }
        binding.anniversaryItem.setOnClickListener {
            toast("纪念日功能开发中")
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

        updateLoveDateAndTime()
        setTimer()
    }

    private fun setTimer() {
        handler.postDelayed({
            if (!isAdded) {
                return@postDelayed
            }
            updateLoveDateAndTime()
            setTimer()
        }, 1000)
    }

    private fun updateLoveDateAndTime() {
        val startTime = LOVER_START_TIME_MILLIS
        val nowtime = System.currentTimeMillis()

        val distance = nowtime - startTime

        val day = distance / (1000 * 24 * 60 * 60)
        val hour = (distance - day * (1000 * 24 * 60 * 60)) / (60 * 60 * 1000)
        val minutes = (distance - day * (1000 * 24 * 60 * 60) - (hour * 60 * 60 * 1000)) / (60 * 1000)
        val seconds = (distance - day * (1000 * 24 * 60 * 60) - (hour * 60 * 60 * 1000) - (minutes * 60 * 1000)) / (1000)

        binding.loveDate.text = "${day}天"
        binding.loveTime.text = resources.getString(R.string.string_time, hour, minutes, seconds)
    }

    private fun toast(content: String) {
        Toast.makeText(context, "\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D$content", Toast.LENGTH_SHORT).show()
    }


}