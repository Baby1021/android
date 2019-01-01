package com.laiyuanwen.android.baby.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laiyuanwen.android.baby.repository.Task
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
            val result = repository.getTasks().await()

            withContext(Dispatchers.Main) {
                tasks.value = result
            }
        }
    }

}