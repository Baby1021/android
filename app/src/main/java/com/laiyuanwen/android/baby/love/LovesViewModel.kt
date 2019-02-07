package com.laiyuanwen.android.baby.love

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.repository.LoveRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Created by laiyuanwen on 2019-01-20.
 */
class LovesViewModel(
        private val repository: LoveRepository
) : ViewModel() {

    val loves: MutableLiveData<List<Love>> = MutableLiveData()
    val reminds: MutableLiveData<List<Love>> = MutableLiveData()
    val commentResult: MutableLiveData<Boolean> = MutableLiveData()

    init {
        fetch()
        fetchReminds()
    }

    private fun fetchReminds() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.getRemindLoves()

                withContext(Dispatchers.Main) {
                    reminds.value = result
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun refresh() {
        fetch()
    }

    fun fetch() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.getLoves()

                withContext(Dispatchers.Main) {
                    loves.value = result
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun comment(content: String, loveId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.comment(content, loveId)

                withContext(Dispatchers.Main) {
                    commentResult.value = true
                }
            } catch (e: Exception) {
                commentResult.value = false
                e.printStackTrace()
            }
        }
    }
}