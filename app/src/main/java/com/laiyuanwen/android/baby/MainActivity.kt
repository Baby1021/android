package com.laiyuanwen.android.baby

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (hasNotification()) {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
    }

    private fun hasNotification(): Boolean {
        return true
    }
}
