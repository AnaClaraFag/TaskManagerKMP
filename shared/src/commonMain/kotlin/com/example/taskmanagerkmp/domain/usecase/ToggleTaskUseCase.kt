package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.ITaskRepository
import kotlinx.datetime.TimeZone

class ToggleTaskUseCase(private val repository: ITaskRepository) {
    suspend operator fun invoke(task: Task): Boolean {
        val now = TimeZone.currentSystemDefault().toString()
        return repository.updateTask(
            task.copy(isCompleted = !task.isCompleted, updatedAt = now)
        )
    }
}
