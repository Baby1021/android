package com.laiyuanwen.android.baby.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laiyuanwen.android.baby.repository.TaskRepository

/**
 * Created by laiyuanwen on 2019/1/1.
 */
class TasksViewModelFactory(
        private val taskRepository: TaskRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksViewModel(taskRepository) as T
    }
}