package com.laiyuanwen.android.baby

import java.util.*

/**
 * Created by laiyuanwen on 2019/1/9.
 */
object Common {

    /**
     * Flutter MethodChannel 参数key
     */
    const val CHANNEL_PAGE_ARGUMENT = "app.channel.page.argument"

    /**
     * Flutter MethodChannel 参数方法
     */
    const val CHANNEL_METHOD_GET_ARGUMENT = "getArgument"
    const val CHANNEL_METHOD_SET_RESULT = "setResult"

    object SharedPreferenceName {
        const val SETTING = "setting"
    }

    object SharedPreferenceKey {
        const val USER_ID = "userId"
        const val PUSH_TOKEN = "push_token"
    }

    object BundleKey {
        /**
         * 是否改变
         */
        const val FLUTTER_LOVE_DETAIL_IS_CHANGE = "isRefresh"
    }

    object ActivityRequestCode {
        const val FLUTTER_RESULT = 1
        const val LOVE_DETAIL = 2
    }

    object PermissionRequestCode {
        const val PRC_WRITE_EXTERNAL_STORAGE = 1 // 应用更新的权限检查
        const val PRC_LOCATION = 2 // 定位的权限检查
    }

    /**
     * 两个人开始时间
     */
    const val LOVER_START_TIME_MILLIS: Long = 1413899526000
}