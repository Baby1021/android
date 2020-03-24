package com.laiyuanwen.android.baby.ui.pages.homepage

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.laiyuanwen.android.baby.Common
import com.laiyuanwen.android.baby.Common.PermissionRequestCode.PRC_BASE
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.platform.PlatformService
import com.laiyuanwen.android.baby.ui.pages.homepage.home.HomeFragment
import com.laiyuanwen.android.baby.ui.pages.homepage.love.LovesFragment
import com.laiyuanwen.android.baby.ui.pages.homepage.takecase.TakeCaseFragment
import com.laiyuanwen.android.baby.ui.pages.homepage.task.TasksFragment
import com.laiyuanwen.android.baby.ui.pages.homepage.user.UserFragment
import com.laiyuanwen.android.baby.util.location.LocationManager
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var mHomeFragment: HomeFragment
    lateinit var mLovesFragment: LovesFragment
    lateinit var mTaskCaseFragment: TakeCaseFragment
    lateinit var mTasksFragment: TasksFragment
    lateinit var mUserFragment: UserFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = home_container as NavHostFragment

        PlatformService.updatePushToken()
        checkPermission()
        checkUpdate()
        location()
        initView()
    }

    /**
     * 检查权限
     */
    private fun checkPermission() {
        if (!EasyPermissions.hasPermissions(this, *LocationManager.PREMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "App基础权限", Common.PermissionRequestCode.PRC_BASE, *LocationManager.PREMISSION)
        }
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
                        navHostFragment.navController.navigate(R.id.homeFragment)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_love -> {
                    if (currentName != LovesFragment::class.java.name) {
                        navHostFragment.navController.navigate(R.id.lovesFragment)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_take_case -> {
                    if (currentName != TakeCaseFragment::class.java.name) {
                        navHostFragment.navController.navigate(R.id.takeCaseFragment)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_tasks -> {
                    if (currentName != TasksFragment::class.java.name) {
                        navHostFragment.navController.navigate(R.id.tasksFragment)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_user -> {
                    if (currentName != UserFragment::class.java.name) {
                        navHostFragment.navController.navigate(R.id.userFragment)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    @AfterPermissionGranted(PRC_BASE)
    fun checkUpdate() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PlatformService.checkUpdate(this, false)
        }
    }

    @AfterPermissionGranted(PRC_BASE)
    fun location() {
        if (EasyPermissions.hasPermissions(this, *LocationManager.PREMISSION)) {
            LocationManager.location()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
