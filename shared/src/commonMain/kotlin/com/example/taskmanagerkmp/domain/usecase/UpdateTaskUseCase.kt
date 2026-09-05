package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.ITaskRepository

class UpdateTaskUseCase(private val repository: ITaskRepository) {
    suspend operator fun invoke(task: Task) = repository.updateTask(task)

}