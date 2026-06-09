package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.TaskRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class AddTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(title: String, description: String): Long {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return repository.addTask(
            Task(
                title = title.trim(),
                description = description.trim(),
                isCompleted = false,
                createdAt = now,
                updatedAt = now,
            )
        )
    }
}
