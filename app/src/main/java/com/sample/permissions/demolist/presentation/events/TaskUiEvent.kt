package com.sample.permissions.demolist.presentation.events

sealed interface TaskUiEvent{
    data class OnTaskQuery(val query: String): TaskUiEvent
    data class OnToggled(val taskId: String): TaskUiEvent
    data object OnRefresh: TaskUiEvent
}