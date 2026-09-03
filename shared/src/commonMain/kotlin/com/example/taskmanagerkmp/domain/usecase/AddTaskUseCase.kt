package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.ITaskRepository
import kotlinx.datetime.TimeZone

class AddTaskUseCase(private val repository: ITaskRepository) {
    suspend operator fun invoke(title: String, description: String): Long {
        require(title.isNotBlank()) { "Title cannot be blank" }
        val now = TimeZone.currentSystemDefault().toString()
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
