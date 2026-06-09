package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.repository.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Long): Boolean = repository.deleteTask(id)
}
