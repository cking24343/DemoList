package com.sample.permissions.demolist.data

import com.sample.permissions.demolist.data.models.TaskDto
import com.sample.permissions.demolist.domain.TaskRepositoryContract
import com.sample.permissions.demolist.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID

internal class TaskRepository : TaskRepositoryContract {

    private val state = MutableStateFlow(initialTasks())

    override fun getTasks(): Flow<List<Task>> =
        state.map { tasks ->
            tasks.map { task ->
                task.toDomain()
            }
        }


    override suspend fun toggleTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }

    // Helpers
    private fun initialTasks(): List<TaskDto> = listOf(
        TaskDto(UUID.randomUUID().toString(), "Walk the dog", false),
        TaskDto(UUID.randomUUID().toString(), "Buy groceries", false),
        TaskDto(UUID.randomUUID().toString(), "Pick up dry cleaning", false),
        TaskDto(UUID.randomUUID().toString(), "Clean house", false),
        TaskDto(UUID.randomUUID().toString(), "Do the laundry", false),
        TaskDto(UUID.randomUUID().toString(), "Mow the lawn", false),
        TaskDto(UUID.randomUUID().toString(), "Cook dinner", false),
    )

}