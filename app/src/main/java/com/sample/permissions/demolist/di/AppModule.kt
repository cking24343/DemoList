package com.sample.permissions.demolist.di

import com.sample.permissions.demolist.data.TaskRepository
import com.sample.permissions.demolist.domain.TaskRepositoryContract
import com.sample.permissions.demolist.presentation.MainActivityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TaskRepositoryContract> { TaskRepository() }

    viewModel { MainActivityViewModel(get()) }
}