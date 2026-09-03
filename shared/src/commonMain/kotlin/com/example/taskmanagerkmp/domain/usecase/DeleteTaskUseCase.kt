package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.repository.ITaskRepository

class DeleteTaskUseCase(private val repository: ITaskRepository) {
    suspend operator fun invoke(id: Long): Boolean = repository.deleteTask(id)
}
