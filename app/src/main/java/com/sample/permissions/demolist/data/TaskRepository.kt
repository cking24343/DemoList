package com.sample.permissions.demolist.data

import com.sample.permissions.demolist.data.models.TaskDto
import com.sample.permissions.demolist.domain.TaskRepositoryContract
import com.sample.permissions.demolist.domain.models.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.UUID

internal class TaskRepository : TaskRepositoryContract {

    private val state = MutableStateFlow(initialTasks())

    override fun getTasks(query: String): Flow<List<Task>> =
        state.map { tasks ->
            tasks
                .filter {
                    it.name.contains(query, ignoreCase = true)
                }.map { task ->
                    task.toDomain()
                }
        }


    override suspend fun toggleTask(taskId: String) {
        state.update { tasks ->
            tasks.map {
                if (it.id == taskId) {
                    it.copy(completed = !it.completed)
                } else {
                    it
                }
            }
        }
    }

    override suspend fun refresh() {
        // simulate a delay
        delay(600)

        state.update { tasks ->
            tasks + TaskDto(
                UUID.randomUUID().toString(),
                "New task (#${tasks.size+1})",
                false,
            )
        }
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