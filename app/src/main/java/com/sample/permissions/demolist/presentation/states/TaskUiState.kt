package com.sample.permissions.demolist.presentation.states

import com.sample.permissions.demolist.presentation.models.TaskUi

sealed class TaskUiState {
    data object Loading : TaskUiState()

    data class Error(
        val message: String,
    ) : TaskUiState()

    data class Content(
        val query: String,
        val isRefreshing: Boolean,
        val tasks: List<TaskUi>,
        val completedCount: Int,
    ) : TaskUiState()
}