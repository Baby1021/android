package com.laiyuanwen.android.baby.util.location

import android.Manifest
import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

/**
 * Created by laiyuanwen on 2020/3/18.
 *
 * 定位工具类
 *
 * 高德地图定位SDK文档：https://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation
 */
object LocationManager {

    val PREMISSION = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var mLocationClient: AMapLocationClient
    private var cacheLocation: AMapLocation? = null
    private var firstLocation: Boolean = true

    fun init(applicationContext: Context) {
        mLocationClient = AMapLocationClient(applicationContext)
        this.location()
    }

    fun destroy() {
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    fun location(callback: AMapLocationListener? = null) {
        val option = AMapLocationClientOption()
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mLocationClient.setLocationOption(option)
        mLocationClient.setLocationListener { location ->

            //停止定位后，本地定位服务并不会被销毁
            mLocationClient.stopLocation()

            if (location != null && location.errorCode == 0) {
                // 缓存定位
                this.cacheLocation = location

                // 上报数据
                if (this.firstLocation) {
                    LocationReport.report(location)
                }

                this.firstLocation = false
                callback?.onLocationChanged(location)
            }
        }

        mLocationClient.stopLocation()
        mLocationClient.startLocation()
    }

    fun getCacheLocation(): AMapLocation? {
        return cacheLocation
    }

    fun getLocation(listener: AMapLocationListener) {
        location(listener)
    }
}