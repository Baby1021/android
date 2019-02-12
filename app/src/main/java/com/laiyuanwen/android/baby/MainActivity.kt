package com.laiyuanwen.android.baby

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.Common.PermissionRequestCode.RC_WRITE_EXTERNAL_STORAGE
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.platform.PlatformService
import com.laiyuanwen.android.baby.ui.home.HomeFragment
import com.laiyuanwen.android.baby.ui.home.HomeFragmentDirections
import com.laiyuanwen.android.baby.ui.love.LovesFragment
import com.laiyuanwen.android.baby.ui.love.LovesFragmentDirections
import com.laiyuanwen.android.baby.ui.takecase.TakeCaseFragment
import com.laiyuanwen.android.baby.ui.task.TasksFragment
import com.laiyuanwen.android.baby.ui.user.UserFragment
import com.laiyuanwen.android.baby.util.getPushToken
import com.laiyuanwen.android.baby.util.getUserId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = home_container as NavHostFragment

        PlatformService.updatePushToken()
        checkUpdate()
        initView()
    }

    private fun initView() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            val currentName = (navHostFragment.navController.currentDestination as FragmentNavigator.Destination).className
            when (currentName) {
                HomeFragment::class.java.name -> {
                    navigation_view.selectedItemId = R.id.navigation_home
                }
                LovesFragment::class.java.name -> {
                    navigation_view.selectedItemId = R.id.navigation_love
                }
                TakeCaseFragment::class.java.name -> {
                    navigation_view.selectedItemId = R.id.navigation_take_case
                }
                TasksFragment::class.java.name -> {
                    navigation_view.selectedItemId = R.id.navigation_tasks
                }
                UserFragment::class.java.name -> {
                    navigation_view.selectedItemId = R.id.navigation_user
                }
            }
        }
        navigation_view.setOnNavigationItemSelectedListener { item ->
            val currentName = (navHostFragment.navController.currentDestination as FragmentNavigator.Destination).className
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (currentName != HomeFragment::class.java.name) {
                        navHostFragment.findNavController().navigate(ActionOnlyNavDirections(R.id.main_to_home))
                        navHostFragment.navController
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_love -> {
                    if (currentName != LovesFragment::class.java.name) {
                        navHostFragment.findNavController().navigate(ActionOnlyNavDirections(R.id.main_to_love))
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_take_case -> {
                    if (currentName != TakeCaseFragment::class.java.name) {
                        navHostFragment.findNavController().navigate(ActionOnlyNavDirections(R.id.main_to_take_case))
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_tasks -> {
                    if (currentName != TasksFragment::class.java.name) {
                        navHostFragment.findNavController().navigate(ActionOnlyNavDirections(R.id.main_to_task))
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_user -> {
                    if (currentName != UserFragment::class.java.name) {
                        navHostFragment.findNavController().navigate(ActionOnlyNavDirections(R.id.main_to_user))
                    }
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE)
    fun checkUpdate() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            PlatformService.checkUpdate(this, false)
        } else {
            EasyPermissions.requestPermissions(this, "缓存数据和下载需要外部写权限哦", Common.PermissionRequestCode.RC_WRITE_EXTERNAL_STORAGE, *perms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
