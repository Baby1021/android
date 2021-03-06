package com.laiyuanwen.android.baby.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laiyuanwen.android.baby.bean.Task
import com.laiyuanwen.android.baby.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by laiyuanwen on 2019/1/1.
 */
class TasksViewModel(
        private val repository: TaskRepository
) : ViewModel() {

    val tasks: MutableLiveData<List<Task>> = MutableLiveData()

    init {
        fetch()
    }

    fun refresh() {
        fetch()
    }

    fun fetch() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val result = repository.getTasks()

                withContext(Dispatchers.Main) {
                    tasks.value = result
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}