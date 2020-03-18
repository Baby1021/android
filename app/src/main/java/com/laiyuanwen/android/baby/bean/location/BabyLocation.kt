package com.laiyuanwen.android.baby.bean.location

/**
 * Created by laiyuanwen on 2020/3/19.
 */

data class BabyLocation(
        // 纬度
        val latitude: Double,
        // 维度
        val longitude: Double,
        // 定位精度
        val accuracy: Float,
        // 海拔
        val altitude: Double,
        // 速度
        val speed: Float,
        // 方向角
        val bearing: Float,
        // 室内定位建筑物Id
        val buildingId: String,
        // 室内定位楼层
        val floor: String,
        // 地址描述
        val address: String,
        // 国家
        val country: String,
        // 省
        val province: String,
        // 城市
        val city: String,
        // 城区:朝阳区
        val district: String,
        // 街道:安宁大街
        val street: String,
        // 街道门牌号:799号
        val streetNum: String,
        // 城市编码
        val cityCode: String,
        // 区域编码
        val adCode: String,
        // 当前位置POI名称:北京市顺义区空港街道吉祥花园社区居民委员会
        val poiName: String,
        // 当前位置所处AOI名称:吉祥花园小区
        val aoiName: String,
        // 设备当前GPS状态
        val gpsAccuracyStatus: Int,
        // 定位信息描述:#csid:a6b78b2d94f74e088a07b6fb3d86bdcf
        val locationDetail: String,
        // 描述：在北京市顺义区空港街道吉祥花园社区居民委员会附近
        val description:String
)