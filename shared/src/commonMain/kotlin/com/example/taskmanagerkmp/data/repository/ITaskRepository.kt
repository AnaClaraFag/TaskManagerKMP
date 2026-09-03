package com.example.taskmanagerkmp.data.repository

import com.example.taskmanagerkmp.data.model.Task

interface ITaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun getTaskById(id: Long): Task?
    suspend fun addTask(task: Task): Long
    suspend fun updateTask(task: Task): Boolean
    suspend fun deleteTask(id: Long): Boolean
}