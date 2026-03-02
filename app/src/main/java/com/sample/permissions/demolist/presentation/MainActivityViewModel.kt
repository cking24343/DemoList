package com.sample.permissions.demolist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.permissions.demolist.domain.TaskRepositoryContract
import com.sample.permissions.demolist.presentation.events.TaskUiEvent
import com.sample.permissions.demolist.presentation.states.TaskUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.map

@OptIn(FlowPreview::class)
class MainActivityViewModel(
    val taskRepository: TaskRepositoryContract,
) : ViewModel() {
    private val query = MutableStateFlow("")
    private val isRefreshing = MutableStateFlow(false)

    private val uiTasks = taskRepository.getTasks()

    private val debouncedQuery = query
        .map { it.trim() }
        .debounce(300)
        .distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val uiState = combine(
        uiTasks,
        query,
        debouncedQuery,
        isRefreshing,
    ) { results, rawQuery, queryText, refreshing ->
        if (rawQuery.isBlank()) {
            return@combine TaskUiState.Content(
                isRefreshing = refreshing,
                query = rawQuery,
                tasks = results.map { it.toUi() },
                completedCount = 0
            )
        }

        results.filter {
            it.name.contains(queryText, true)
        }.let { filteredTasks ->
            TaskUiState.Content(
                query = rawQuery,
                isRefreshing = refreshing,
                tasks = filteredTasks.map { it.toUi() },
                completedCount = filteredTasks.count { it.completed }
            ) as TaskUiState
        }
    }
        .onStart {
            emit(TaskUiState.Loading)
        }
        .catch { exception ->
            emit(TaskUiState.Error(message = "Error: ${exception.message ?: "unknown"}"))
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            TaskUiState.Loading,
        )

    // Handle events
    fun onEvent(event: TaskUiEvent) {
        when (event) {
            is TaskUiEvent.OnRefresh -> {
                refreshTaskList()
            }

            is TaskUiEvent.OnToggled -> {
                toggleTask(event.taskId)
            }

            is TaskUiEvent.OnTaskQuery -> {
                query.value = event.query
            }
        }
    }

    fun toggleTask(taskId: String) = viewModelScope.launch {
        taskRepository.toggleTask(taskId)
    }

    fun refreshTaskList() = viewModelScope.launch {
        isRefreshing.value = true
        runCatching {
            taskRepository.refresh()
        }.onSuccess {
            isRefreshing.value = false
        }.onFailure {
            isRefreshing.value = false
        }
    }
}
