package com.sample.permissions.demolist.data

import com.sample.permissions.demolist.data.models.TaskDto
import com.sample.permissions.demolist.domain.models.Task

fun TaskDto.toDomain() = Task(
    id = id,
    name = name,
    completed = completed,
)