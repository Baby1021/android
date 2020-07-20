package com.laiyuanwen.android.baby.util

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Created by laiyuanwen on 2019-01-19.
 */

fun getSp(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

