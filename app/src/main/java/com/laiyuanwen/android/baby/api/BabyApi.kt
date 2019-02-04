package com.laiyuanwen.android.baby.api

import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.bean.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by laiyuanwen on 2018/12/31.
 */
interface BabyApi {

    @GET("api/v1/task")
    fun getTasks(@Query("userId") userId: String, @Query("page") page: Int, @Query("limit") limit: Int): Deferred<List<Task>>

    @GET("api/v1/love")
    fun getLoves(@Query("userId") userId: String, @Query("page") page: Int, @Query("limit") limit: Int): Deferred<List<Love>>

    @GET("api/v1/love/")
    fun getRemindLoves(@Query("userId/remind") userId: String): Deferred<List<Love>>

    @GET("api/v1/surprise")
    fun getSurprise(@Query("userId") userId: String): Deferred<SurpriseResponse>

    @GET("api/v1/app/checkUpdate")
    fun checkUpdate(@Query("version") version: Int): Deferred<NewVersionResult>

    // 评论
    @POST("api/v1/lovecomment")
    fun comment(@retrofit2.http.Body comment: JsonObject): Deferred<Body<Long>>
}