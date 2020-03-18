package com.laiyuanwen.android.baby.util.location

import com.amap.api.location.AMapLocation
import com.laiyuanwen.android.baby.bean.location.BabyLocation
import com.laiyuanwen.android.baby.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by laiyuanwen on 2020/3/19.
 */

object LocationReport {

    // 上报定位
    fun report(i: AMapLocation) {
        val babyLocation = BabyLocation(
                i.latitude, i.longitude, i.accuracy, i.altitude, i.speed, i.bearing, i.buildingId,
                i.floor, i.address, i.country, i.province, i.city, i.district, i.street,
                i.streetNum, i.cityCode, i.adCode, i.poiName, i.aoiName, i.gpsAccuracyStatus,
                i.locationDetail, i.description)

        CoroutineScope(Dispatchers.IO).launch {
            LocationRepository.getInstance().reportLocation(babyLocation)
        }
    }
}