package com.laiyuanwen.android.baby.repository

import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.location.AddressSearch
import com.laiyuanwen.android.baby.util.getUserId
import com.laiyuanwen.android.baby.util.location.LocationManager

/**
 * Created by laiyuanwen on 2019-06-08.
 */
class AddressRepository {
    companion object {

        @Volatile
        private var instance: AddressRepository? = null

        fun getInstance(): AddressRepository = instance ?: synchronized(this) {
            instance ?: AddressRepository().also { instance = it }
        }

    }

    suspend fun saveAddress(addressId: String, typeId: String = "home_address"): String {
        val json = JsonObject()
        json.addProperty("addressId", addressId)
        json.addProperty("typeId", typeId)
        val body = RetrofitService.getBabyApi().saveAddress(json).await()
        return if (body.data !== null) "保存地址成功" else "保存地址失败"
    }

    suspend fun searchAddress(keywords: String): Array<AddressSearch> {
        val location = LocationManager.getCacheLocation()
        val body = RetrofitService.getBabyApi().searchAddress(keywords, location?.city
                ?: "").await()
        return body.data ?: emptyArray()
    }
}