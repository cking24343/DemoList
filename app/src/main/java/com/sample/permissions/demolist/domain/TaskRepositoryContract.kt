package com.sample.permissions.demolist.domain

import com.sample.permissions.demolist.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepositoryContract {

    fun getTasks(): Flow<List<Task>>

    suspend fun toggleTask(taskId: String)
    suspend fun refresh()
}