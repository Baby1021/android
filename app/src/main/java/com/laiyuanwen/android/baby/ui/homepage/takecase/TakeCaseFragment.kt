package com.laiyuanwen.android.baby.ui.homepage.takecase

import android.os.Bundle
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.util.setStatusBarColor
import io.flutter.facade.FlutterFragment

/**
 * Created by laiyuanwen on 2019-02-11.
 *
 * 关爱
 */
class TakeCaseFragment : FlutterFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (arguments == null) {
            arguments = Bundle()
        }
        arguments!!.putString(ARG_ROUTE, FLUTTER_TAKE_CASE_PATH)
        super.onCreate(savedInstanceState)
        setStatusBarColor(activity!!, R.color.baby_white)
    }

    companion object {
        const val FLUTTER_TAKE_CASE_PATH = "/router/main/takecase"
    }
}