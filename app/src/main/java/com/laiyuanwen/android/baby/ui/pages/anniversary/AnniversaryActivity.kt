package com.laiyuanwen.android.baby.ui.pages.anniversary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.util.setStatusBarColor

class AnniversaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anniversary)

        setStatusBarColor(
                this,
                ContextCompat.getColor(this, R.color.colorPrimaryAnniversary)
        )

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, AnniversaryFragment())
                .commitNowAllowingStateLoss()
    }
}
