package com.example.taskmanagerkmp.domain.usecase

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(): List<Task> = repository.getAllTasks()
}
