package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.repository.ITaskRepository

class GetTaskByIdUseCase(private val repository: ITaskRepository) {
    suspend operator fun invoke(taskId: Long) = repository.getTaskById(taskId)
}