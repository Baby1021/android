package com.laiyuanwen.android.baby.ui.pages.love

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.util.setStatusBarColor

/**
 * Created by laiyuanwen on 2020/7/25.
 */
class PublishLoveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_love)

        setStatusBarColor(this, ContextCompat.getColor(this, R.color.baby_white))

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, PublishLoveFragment())
                .commitNowAllowingStateLoss()
    }
}