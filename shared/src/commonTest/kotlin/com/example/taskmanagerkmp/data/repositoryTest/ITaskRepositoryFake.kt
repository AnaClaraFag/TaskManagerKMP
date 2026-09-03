package com.example.taskmanagerkmp.data.repositoryTest

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.ITaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.TimeZone

class ITaskRepositoryFake : ITaskRepository {

    private val tasks = MutableStateFlow<List<Task>>(emptyList())
    private var nextId = 1L

    override suspend fun getAllTasks(): List<Task> = tasks.value

    override suspend fun getTaskById(id: Long): Task? =
        tasks.value.find { it.id == id }

    override suspend fun addTask(task: Task): Long{
        val now = TimeZone.currentSystemDefault().toString()
        tasks.value = tasks.value + task.copy(id = nextId++, createdAt = now, updatedAt = now)
        return nextId
    }

    override suspend fun updateTask(task: Task): Boolean {
        tasks.value = tasks.value.map { if (it.id == task.id) task else it }
        return true
    }

    override suspend fun deleteTask(id: Long): Boolean {
        tasks.value = tasks.value.filter { it.id != id }
        return true
    }

    // Helpers para setup dos testes
    fun givenTasks(vararg task: Task) {
        tasks.value = task.toList()
    }

    fun currentTasks(): List<Task> = tasks.value

}