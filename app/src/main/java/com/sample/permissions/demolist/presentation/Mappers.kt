package com.sample.permissions.demolist.presentation

import com.sample.permissions.demolist.domain.models.Task
import com.sample.permissions.demolist.presentation.models.TaskUi

fun Task.toUi() = TaskUi(
    id = id,
    title = name,
    completed = completed,
)
