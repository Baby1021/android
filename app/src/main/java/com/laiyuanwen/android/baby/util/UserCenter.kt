package com.laiyuanwen.android.baby.util

import android.content.Context
import androidx.core.content.edit
import com.laiyuanwen.android.baby.Common
import com.laiyuanwen.android.baby.Common.SharedPreferenceKey.USER_ID
import com.laiyuanwen.android.baby.BabyApplication.Companion.getApplicationContext

/**
 * Created by laiyuanwen on 2019-01-19.
 */

fun isLogin(context: Context): Boolean {
    // StringUtils.isBlank(null)      = true
    // StringUtils.isBlank("")        = true
    // StringUtils.isBlank(" ")       = true
    // StringUtils.isBlank("bob")     = false
    // StringUtils.isBlank("  bob  ") = false
    return getSp(context).getString(Common.SharedPreferenceKey.USER_ID, "")?.isNotBlank() ?: false
}

fun getUserId(): String = getSp(getApplicationContext()).getString(USER_ID, "") ?: ""

fun logout() {
    getSp(getApplicationContext()).edit {
        this.putString(Common.SharedPreferenceKey.USER_ID, "")
    }
}

fun saveLogin(context: Context, userId: String) {
    getSp(context).edit {
        this.putString(Common.SharedPreferenceKey.USER_ID, userId)
    }
}

