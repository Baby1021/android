package com.laiyuanwen.android.baby.repository

import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.api.HttpBody
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.Anniversary
import com.laiyuanwen.android.baby.util.getUserId

/**
 * Created by laiyuanwen on 2019-06-08.
 */
class AnniversaryRepository {
    companion object {

        @Volatile
        private var instance: AnniversaryRepository? = null

        fun getInstance(): AnniversaryRepository = instance ?: synchronized(this) {
            instance ?: AnniversaryRepository().also { instance = it }
        }

    }

    suspend fun addAnniversary(name: String, year: Int, month: Int, dayOfMonth: Int): HttpBody<Any> {

        val json = JsonObject()

        json.addProperty("name", name)
        json.addProperty("year", year)
        json.addProperty("month", month)
        json.addProperty("day", dayOfMonth)
        json.addProperty("userId", getUserId())

        return RetrofitService.getBabyApi().addAnniversary(json).await()
    }

    suspend fun getAnniversary(): List<Anniversary> {
        val body = RetrofitService.getBabyApi().getAnniversaries(getUserId()).await()
        return body.data ?: emptyList()
    }
}