package com.laiyuanwen.android.baby.api

import com.laiyuanwen.android.baby.bean.NewVersionResult
import com.laiyuanwen.android.baby.repository.Task
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by laiyuanwen on 2018/12/31.
 */
interface TeamworkApi {

    @GET("api/v1/task")
    fun getTasks(@Query("userId") userId: String, @Query("page") page: Int, @Query("limit") limit: Int): Deferred<List<Task>>

    @GET("api/v1/app/checkUpdate")
    fun checkUpdate(@Query("version") version: Int): Deferred<NewVersionResult>
}