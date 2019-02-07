package com.laiyuanwen.android.baby.repository

import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.api.HttpBody
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.util.getUserId

/**
 * Created by laiyuanwen on 2019-01-20.
 */
class LoveRepository {
    companion object {

        @Volatile
        private var instance: LoveRepository? = null

        fun getInstance(): LoveRepository = instance ?: synchronized(this) {
            instance ?: LoveRepository().also { instance = it }
        }

    }

    suspend fun getLoves(): List<Love> {
        return RetrofitService.getBabyApi().getLovesAsync(getUserId(), 1, 10).await()
    }

    suspend fun getRemindLoves(): List<Love> {
        return RetrofitService.getBabyApi().getRemindLovesAsync(getUserId()).await()
    }

    suspend fun comment(content: String, loveId: Long): HttpBody<Long> {
        val map = JsonObject()
        val comment = JsonObject()
        comment.addProperty("userId", getUserId())
        comment.addProperty("content", content)
        comment.addProperty("loveId", loveId)
        map.add("comment", comment)
        return RetrofitService.getBabyApi().commentAsync(map).await()
    }

    suspend fun exposureRemind(loveId: Long): HttpBody<Any> {

        val json = JsonObject()

        json.addProperty("loveId", loveId)
        json.addProperty("remind", "true")

        return RetrofitService.getBabyApi().exposureRemindAsync(json).await()
    }
}