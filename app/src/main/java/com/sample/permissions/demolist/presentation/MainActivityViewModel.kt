package com.sample.permissions.demolist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.permissions.demolist.domain.TaskRepositoryContract
import com.sample.permissions.demolist.presentation.events.TaskUiEvent
import com.sample.permissions.demolist.presentation.states.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.map

class MainActivityViewModel(
    val taskRepository: TaskRepositoryContract,
) : ViewModel() {
    private val query = MutableStateFlow("")
    private val isRefreshing = MutableStateFlow(false)

    val uiState = combine(
        taskRepository.getTasks(),
        query,
        isRefreshing,
    ) { tasks, q, refreshing ->
        // TODO: refactor this to implement type ahead...
        TaskUiState.Content(
            query = q,
            tasks = tasks.map { it.toUi() },
            completedCount = tasks.size,
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
