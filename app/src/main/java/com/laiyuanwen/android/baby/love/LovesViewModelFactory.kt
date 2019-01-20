package com.laiyuanwen.android.baby.love

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laiyuanwen.android.baby.repository.LoveRepository
import com.laiyuanwen.android.baby.repository.TaskRepository

/**
 * Created by laiyuanwen on 2019-01-20.
 */
class LovesViewModelFactory(
        private val loveRepository: LoveRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LovesViewModel(loveRepository) as T
    }
}