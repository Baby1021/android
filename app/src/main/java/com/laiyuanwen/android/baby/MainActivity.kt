package com.laiyuanwen.android.baby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.Surprise
import com.laiyuanwen.android.baby.surprise.ImageSurpriseDialogFragment
import com.laiyuanwen.android.baby.util.getUserId
import com.tencent.bugly.Bugly
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * todo 记得注销广播
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Bugly.init(applicationContext, "7484f50fa8", BuildConfig.DEBUG)


        fetchSurprise()
    }

    private fun fetchSurprise() {

        CoroutineScope(Dispatchers.IO).launch {
            var result = RetrofitService.getBabyApi().getSurprise(getUserId()).await()

            if (result.data == null) {
                return@launch
            }

            val data: Surprise = result.data!!

            withContext(Dispatchers.Main) {
                showSurprise(data)
            }
        }
    }

    private fun showSurprise(surprise: Surprise) {
        ImageSurpriseDialogFragment.getInstance(surprise).show(supportFragmentManager, "")
    }
}
