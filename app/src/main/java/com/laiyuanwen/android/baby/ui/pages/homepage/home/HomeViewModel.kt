package com.laiyuanwen.android.baby.ui.pages.homepage.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laiyuanwen.android.baby.Common
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by laiyuanwen on 2019-05-18.
 */
data class Time(val day: Long, val hour: Long, val minutes: Long, val seconds: Long)

class HomeViewModel() : ViewModel() {

    private val pool = Executors.newScheduledThreadPool(1)
    private val mainScope = CoroutineScope(Dispatchers.Main + Job())

    val time: MutableLiveData<Time> = MutableLiveData()

    init {
        updateTime()
        loop()
    }

    private fun loop() {
        pool.schedule({
            updateTime()
            loop()
        }, 1, TimeUnit.SECONDS)
    }

    private fun updateTime() {
        val startTime = Common.LOVER_START_TIME_MILLIS
        val nowtime = System.currentTimeMillis()

        val distance = nowtime - startTime

        val day = distance / (1000 * 24 * 60 * 60)
        val hour = (distance - day * (1000 * 24 * 60 * 60)) / (60 * 60 * 1000)
        val minutes = (distance - day * (1000 * 24 * 60 * 60) - (hour * 60 * 60 * 1000)) / (60 * 1000)
        val seconds = (distance - day * (1000 * 24 * 60 * 60) - (hour * 60 * 60 * 1000) - (minutes * 60 * 1000)) / (1000)

        mainScope.launch {
            time.value = Time(day, hour, minutes, seconds)
        }
    }

    fun stop() {
        pool.shutdown()
    }
}