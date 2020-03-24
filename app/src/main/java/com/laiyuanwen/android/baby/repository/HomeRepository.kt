package com.laiyuanwen.android.baby.repository

import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.response.HomeInfo
import com.laiyuanwen.android.baby.bean.response.HomeInfoUser

/**
 * Created by laiyuanwen on 2019-06-08.
 */
class HomeRepository {
    companion object {

        @Volatile
        private var instance: HomeRepository? = null

        fun getInstance(): HomeRepository = instance ?: synchronized(this) {
            instance ?: HomeRepository().also { instance = it }
        }

    }

    suspend fun getHomeInfo(): HomeInfo {
        val body = RetrofitService.getBabyApi().getHomeInfo().await()
        return body.data ?: HomeInfo(HomeInfoUser("", "", ""), HomeInfoUser("", "", ""))
    }

}