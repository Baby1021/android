package com.laiyuanwen.android.baby.repository

import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.util.getUserId
import kotlinx.coroutines.Deferred

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
}