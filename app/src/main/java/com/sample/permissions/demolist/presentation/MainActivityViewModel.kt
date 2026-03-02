package com.sample.permissions.demolist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.permissions.demolist.domain.TaskRepositoryContract
import com.sample.permissions.demolist.presentation.events.TaskUiEvent
import com.sample.permissions.demolist.presentation.states.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainActivityViewModel(
    val taskRepository: TaskRepositoryContract,
) : ViewModel() {
    private val query = MutableStateFlow("")
    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        query,
        isRefreshing
    ) { q, refreshing ->
        // TODO: replace with typeahead...stubbing for now...
        TaskUiState.Content(
            query = q,
            tasks = emptyList(),
            completedCount = 0,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        TaskUiState.Loading,
    )

    // Handle events
    fun onEvent(event: TaskUiEvent) {
        when (event) {
            is TaskUiEvent.OnRefresh -> {
                // TODO: implement refresh call...
            }

            is TaskUiEvent.OnToggled -> {
                // TODO: implement update to toggle a task...
            }

            is TaskUiEvent.OnTaskQuery -> {
                // TODO: implement typeahead...
            }
        }
    }
}
