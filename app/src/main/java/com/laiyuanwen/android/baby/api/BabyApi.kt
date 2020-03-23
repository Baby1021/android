package com.laiyuanwen.android.baby.api

import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.bean.*
import com.laiyuanwen.android.baby.bean.location.AddressSearch
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * Created by laiyuanwen on 2018/12/31.
 */
interface BabyApi {

    @GET("api/v1/task")
    fun getTasksAsync(@Query("userId") userId: String, @Query("page") page: Int, @Query("limit") limit: Int): Deferred<List<Task>>

    /**
     * Love列表
     */
    @GET("api/v1/love")
    fun getLovesAsync(@Query("userId") userId: String, @Query("page") page: Int, @Query("limit") limit: Int): Deferred<List<Love>>

    /**
     * love提醒列表
     */
    @GET("api/v1/love/remind")
    fun getRemindLovesAsync(@Query("userId") userId: String): Deferred<List<Love>>

    /**
     * 运营活动
     */
    @GET("api/v1/surprise")
    fun getSurpriseAsync(@Query("userId") userId: String): Deferred<SurpriseResponse>

    /**
     * 检测更新
     */
    @GET("api/v1/app/checkUpdate")
    fun checkUpdateAsync(@Query("version") version: Int): Deferred<NewVersionResult>

    /**
     * 评论Love
     */
    @POST("api/v1/lovecomment")
    fun commentAsync(@Body comment: JsonObject): Deferred<HttpBody<Long>>

    /**
     * love 提醒曝光
     */
    @POST("api/v1/love/remind")
    fun exposureRemindAsync(@Body data: JsonObject): Deferred<HttpBody<Any>>

    /**
     * 更新PushToken
     */
    @PUT("api/v1/user/pushToken")
    fun uploadPushToken(@Body data: JsonObject): Deferred<HttpBody<Any>>

    @POST("api/v1/anniversary")
    fun addAnniversary(@Body data: JsonObject): Deferred<HttpBody<Any>>

    @GET("api/v1/anniversary")
    fun getAnniversaries(@Query("userId") userId: String): Deferred<HttpBody<List<Anniversary>>>

    @POST("api/v1/location/report")
    fun reportLocation(@Body data: JsonObject): Deferred<HttpBody<Any>>

    @GET("api/v1/location/search")
    fun searchAddress(@Query("keywords") keywords: String, @Query("city") city: String?): Deferred<HttpBody<Array<AddressSearch>>>

    @POST("api/v1/location/save")
    fun saveAddress(@Body data: JsonObject): Deferred<HttpBody<Any>>
}