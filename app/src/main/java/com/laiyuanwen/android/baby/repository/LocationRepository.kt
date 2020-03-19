package com.laiyuanwen.android.baby.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.location.BabyLocation
import com.laiyuanwen.android.baby.util.getUserId
import org.json.JSONObject

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
            val json = Gson().toJsonTree(data).asJsonObject
            json.addProperty("userId", getUserId())
            RetrofitService.getBabyApi().reportLocation(json).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}