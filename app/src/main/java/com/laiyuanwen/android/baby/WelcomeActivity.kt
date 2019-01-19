package com.laiyuanwen.android.baby

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.laiyuanwen.android.baby.Common.SharedPreferenceKey.USER_ID
import com.laiyuanwen.android.baby.util.getSp
import com.laiyuanwen.android.baby.util.isLogin

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (isLogin(this)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}
