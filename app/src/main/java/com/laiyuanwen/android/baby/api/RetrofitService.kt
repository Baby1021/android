package com.laiyuanwen.android.baby.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by laiyuanwen on 2018/12/31.
 */
class RetrofitService {
    companion object {
        @JvmField
        val retrofit = Retrofit.Builder()
                .baseUrl("http://39.108.227.137:3000")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @JvmField
        val teamworkApi = retrofit.create(TeamworkApi::class.java)

        fun getTeamworkApi() = teamworkApi
    }
}