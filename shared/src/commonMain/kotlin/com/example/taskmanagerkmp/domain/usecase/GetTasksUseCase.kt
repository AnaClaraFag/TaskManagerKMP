package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.ITaskRepository

class GetTasksUseCase(private val repository: ITaskRepository) {
    suspend operator fun invoke(): List<Task> = repository.getAllTasks()
}
