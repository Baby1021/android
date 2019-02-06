package com.laiyuanwen.android.baby.repository

import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.api.Body
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.util.getUserId
import kotlinx.coroutines.Deferred
import java.lang.reflect.MalformedParameterizedTypeException

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

    fun getLoves(): Deferred<List<Love>> {
        return RetrofitService.getBabyApi().getLoves(getUserId(), 1, 10)
    }

    fun getRemindLoves(): Deferred<List<Love>> {
        return RetrofitService.getBabyApi().getRemindLoves(getUserId())
    }

    fun comment(content: String, loveId: Long): Deferred<Body<Long>> {
        val map = JsonObject()
        val comment = JsonObject()
        comment.addProperty("userId", getUserId())
        comment.addProperty("content", content)
        comment.addProperty("loveId", loveId)
        map.add("comment", comment)
        return RetrofitService.getBabyApi().comment(map)
    }

    fun exposureRemind(loveId: Long): Deferred<Body<Any>> {

        val json = JsonObject()

        json.addProperty("loveId", loveId)
        json.addProperty("remind", "true")

        return RetrofitService.getBabyApi().exposureRemind(json)
    }
}