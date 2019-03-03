package com.laiyuanwen.android.baby

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.laiyuanwen.android.baby.ui.MainActivity
import com.laiyuanwen.android.baby.util.saveLogin
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            val userId = userId.text.toString();
            val password = password.text.toString();
            if ((userId == "laiyuanwen" && password == "0528") || (userId == "lizhuohua" && password == "0122") || (userId == "test" && password == "test") ||(userId == "test2" && password == "test") ) {
                saveLogin(this, userId)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                AlertDialog.Builder(this)
                        .setTitle("登录失败")
                        .setMessage("登录失败啦,是不是写错密码啦")
                        .show()
            }
        }
    }

}
