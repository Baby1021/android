package com.laiyuanwen.android.baby.inject

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.laiyuanwen.android.baby.love.LovesViewModelFactory
import com.laiyuanwen.android.baby.repository.LoveRepository
import com.laiyuanwen.android.baby.repository.TaskRepository
import com.laiyuanwen.android.baby.tasks.TasksViewModelFactory

/**
 * Created by laiyuanwen on 2019/1/1.
 *
 * 抽离注入的工具类，为以后接入Dagger2铺垫
 *
 * 功能：
 * 1. 提供各种单例的入口
 */

object Injector {

    fun provideTasksRepository(context: Context): TaskRepository {
        return TaskRepository.getInstance()
    }

    fun provideLovesRepository(context: Context): LoveRepository {
        return LoveRepository.getInstance()
    }

    fun provideTasksViewModelFactory(context: Context): ViewModelProvider.Factory {
        return TasksViewModelFactory(provideTasksRepository(context))
    }

    fun provideLovesViewModelFactory(context: Context): ViewModelProvider.Factory {
        return LovesViewModelFactory(provideLovesRepository(context))
    }

}

