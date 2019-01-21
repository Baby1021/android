package com.laiyuanwen.android.baby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.laiyuanwen.android.baby.surprise.TextSurpriseDialogFragment
import com.tencent.bugly.Bugly

/**
 * todo 记得注销广播
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Bugly.init(applicationContext, "7484f50fa8", BuildConfig.DEBUG)

        if (hasNotification()) {
            TextSurpriseDialogFragment().show(supportFragmentManager, "")
        }
    }

    private fun hasNotification(): Boolean {
        return false
    }
}
