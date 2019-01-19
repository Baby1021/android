package com.laiyuanwen.android.baby.repository

import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.util.getUserId
import kotlinx.coroutines.*

/**
 * Created by laiyuanwen on 2019/1/1.
 */
class TaskRepository {

    companion object {

        @Volatile
        private var instance: TaskRepository? = null

        fun getInstance(): TaskRepository = instance ?: synchronized(this) {
            instance ?: TaskRepository().also { instance = it }
        }

    }

    fun getTasks(): Deferred<List<Task>> {
        return RetrofitService.getBabyApi().getTasks(getUserId(), 1, 10)
    }
}