package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.TaskRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class ToggleTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task): Boolean {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return repository.updateTask(
            task.copy(isCompleted = !task.isCompleted, updatedAt = now)
        )
    }
}
