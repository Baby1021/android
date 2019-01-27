package com.laiyuanwen.android.baby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.bean.Surprise
import com.laiyuanwen.android.baby.surprise.ImageSurpriseDialogFragment
import com.laiyuanwen.android.baby.util.BuglyCenter
import com.laiyuanwen.android.baby.util.getUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BuglyCenter.init(applicationContext)

        fetchSurprise()
    }

    private fun fetchSurprise() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = RetrofitService.getBabyApi().getSurprise(getUserId()).await()

                if (result.data == null) {
                    return@launch
                }

                val data: Surprise = result.data

                withContext(Dispatchers.Main) {
                    showSurprise(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showSurprise(surprise: Surprise) {
        ImageSurpriseDialogFragment.getInstance(surprise).show(supportFragmentManager, "")
    }
}
