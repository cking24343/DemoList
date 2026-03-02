package com.sample.permissions.demolist.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.permissions.demolist.presentation.events.TaskUiEvent
import com.sample.permissions.demolist.presentation.models.TaskUi
import com.sample.permissions.demolist.presentation.states.TaskUiState

@Composable
fun TaskScreen(
    state: TaskUiState,
    modifier: Modifier = Modifier,
    onEvent: (TaskUiEvent) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            is TaskUiState.Loading -> {
                LoadingContent()
            }

            is TaskUiState.Content -> {
                ScreenContent(
                    query = state.query,
                    taskList = state.tasks,
                    tasksCompleted = state.completedCount,
                    onTaskToggled = { taskId ->
                        onEvent(TaskUiEvent.OnToggled(taskId))
                    }
                )
            }

            is TaskUiState.Error -> {
                ErrorContent(
                    errorMessage = state.message,
                    onRetry = { onEvent(TaskUiEvent.OnRefresh) }
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    errorMessage: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = errorMessage,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onRetry() }
        ) {
            Text(
                text = "Try again",
            )
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    query: String = "",
    taskList: List<TaskUi> = emptyList(),
    tasksCompleted: Int = 0,
    onQuery: (String) -> Unit = {},
    onTaskToggled: (String) -> Unit = {}
) {
    val searchQuery by remember { mutableStateOf(query) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {

        // TODO: Display a search field for querying a task from a list of tasks, ie typeahead
        OutlinedTextField(
            label = {
                Text(text = "Filter Tasks")
            },
            value = searchQuery,
            onValueChange = { newQuery ->
                onQuery(newQuery)
            }
        )

        // TODO: Display the list of tasks
        LazyColumn {
            items(items = taskList, key = { item -> item.id }) { task ->
                TaskRow(
                    task = task,
                    onChecked = onTaskToggled,
                )
            }
        }

        // TODO - Optional: Add pull to refresh for reloading the tasks
    }
}

@Composable
private fun TaskRow(
    task: TaskUi,
    modifier: Modifier = Modifier,
    onChecked: (String) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = task.title
        )

        Checkbox(
            checked = task.completed,
            onCheckedChange = {
                onChecked(task.id)
            }
        )
    }

    HorizontalDivider(
        thickness = 2.dp,
        modifier = modifier.padding(horizontal = 8.dp)
    )
}

@Preview
@Composable
private fun PreviewTaskScreen() {
    TaskScreen(
        state = TaskUiState.Loading
    )
}