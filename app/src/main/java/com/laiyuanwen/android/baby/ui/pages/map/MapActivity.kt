package com.laiyuanwen.android.baby.ui.pages.map

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.util.toast
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : AppCompatActivity() {

    lateinit var aMap: AMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        map_view.onCreate(savedInstanceState)
        aMap = map_view.map

        initLocation()
    }

    fun initLocation() {
        val style = MyLocationStyle()
        style.interval(2000)
        // 设置蓝点类型
        this.initPointType(style)
        // 图标（一定要用图片)
        style.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_update)))

        // 缩放比例
//        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(location.latitude, location.longitude), 17.5F, 0F, 0F)))
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17.5F))


        val latLng = LatLng(39.906901, 116.397972)
        val marker = aMap.addMarker(MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"))

        val circle = aMap.addCircle(CircleOptions().center(latLng).radius(1000.0).fillColor(Color.argb(0.5F, 1F, 1F, 1F)).strokeColor(Color.argb(0.5F, 1F, 1F, 1F)).strokeWidth(15f))

        aMap.setOnInfoWindowClickListener {
//            toast(this, "window info")
            marker.hideInfoWindow()
            aMap.isMyLocationEnabled
            aMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                            LatLng(aMap.myLocation.latitude, aMap.myLocation.longitude), 17F, 0F, 0F)))
            // 重新定位了
            aMap.myLocationStyle = style
        }

        location.setOnClickListener {
            aMap.myLocationStyle = style
        }

        aMap.uiSettings.isMyLocationButtonEnabled = true; //显示默认的定位按钮
        aMap.myLocationStyle = style
        aMap.isMyLocationEnabled = true
        aMap.setOnMyLocationChangeListener { location ->
            toast(this, "定位")
//            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(location.latitude, location.longitude), 17.5F, 0F, 0F)))
        }
    }

    fun initPointType(style: MyLocationStyle) {
        //只定位一次。
//        style.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
//        //定位一次，且将视角移动到地图中心点。
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//        //连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        style.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
//        //连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        style.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
//        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
//        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
//        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
//        //连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        style.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
//        //连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
//        style.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map_view.onSaveInstanceState(outState)
    }

}
