package com.laiyuanwen.android.baby.repository

import android.content.Context
import androidx.core.content.edit
import com.laiyuanwen.android.baby.BabyApplication
import com.laiyuanwen.android.baby.Common

/**
 * Created by laiyuanwen on 2020/1/30.
 *
 * 系统设置
 */
class SettingRepository {

    companion object {
        @Volatile
        private var instance: SettingRepository? = null

        fun getInstance(): SettingRepository = instance ?: synchronized(this) {
            instance ?: SettingRepository().also { instance = it }
        }
    }

    fun saveDebugHost(host: String) {
        BabyApplication.getApplicationContext()
                .getSharedPreferences(Common.SharedPreferenceName.SETTING, Context.MODE_PRIVATE)
                .edit(true) {
                    putString("debug_h5_server", host)
                }
    }

    fun getDebugHost(): String = BabyApplication.getApplicationContext()
            .getSharedPreferences(Common.SharedPreferenceName.SETTING, Context.MODE_PRIVATE)
            .getString("debug_h5_server", "http://") as String

    fun isDebug(): Boolean = BabyApplication.getApplicationContext()
            .getSharedPreferences(Common.SharedPreferenceName.SETTING, Context.MODE_PRIVATE)
            .getBoolean("is_baby_app_debug", false)
}