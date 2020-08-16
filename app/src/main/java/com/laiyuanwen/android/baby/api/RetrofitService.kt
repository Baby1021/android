package com.laiyuanwen.android.baby.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.laiyuanwen.android.baby.util.getUserId
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by laiyuanwen on 2018/12/31.
 */
object RetrofitService {

    const val HOST = "http://39.108.227.137:7001"

    @JvmField
    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(HOST)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()

    @JvmField
    val babyApi: BabyApi = retrofit.create(BabyApi::class.java)

    fun getBabyApi(): BabyApi = babyApi

    private fun getClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url

            val url: HttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("userId", getUserId())
                    .build()

            val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)

            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        return builder.build()
    }
}