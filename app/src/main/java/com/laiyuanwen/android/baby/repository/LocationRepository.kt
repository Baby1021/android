package com.laiyuanwen.android.baby.repository

import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.location.BabyLocation

/**
 * Created by laiyuanwen on 2019-06-08.
 */
class LocationRepository {
    companion object {

        @Volatile
        private var instance: LocationRepository? = null

        fun getInstance(): LocationRepository = instance ?: synchronized(this) {
            instance ?: LocationRepository().also { instance = it }
        }

    }

    suspend fun reportLocation(data: BabyLocation) {
        try {
            RetrofitService.getBabyApi().reportLocation(data).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}