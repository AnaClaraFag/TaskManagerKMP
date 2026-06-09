package com.example.taskmanagerkmp.data.repository

import com.example.taskmanagerkmp.data.local.DatabaseDriverFactory
import com.example.taskmanagerkmp.data.local.createDatabase
import com.example.taskmanagerkmp.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime

class TaskRepositoryImpl(driverFactory: DatabaseDriverFactory) : TaskRepository {

    private val queries = createDatabase(driverFactory).taskQueries

    /** Maps a raw `Task` table row to the domain [Task] model. */
    private fun map(
        id: Long,
        title: String,
        description: String,
        isCompleted: Boolean,
        createdAt: String,
        updatedAt: String,
    ) = Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        createdAt = LocalDateTime.parse(createdAt),
        updatedAt = LocalDateTime.parse(updatedAt),
    )

    override suspend fun getAllTasks(): List<Task> = withContext(Dispatchers.Default) {
        queries.selectAll(::map).executeAsList()
    }

    override suspend fun getTaskById(id: Long): Task? = withContext(Dispatchers.Default) {
        queries.selectById(id, ::map).executeAsOneOrNull()
    }

    override suspend fun addTask(task: Task): Long = withContext(Dispatchers.Default) {
        queries.transactionWithResult {
            queries.insert(
                title = task.title,
                description = task.description,
                isCompleted = task.isCompleted,
                createdAt = task.createdAt.toString(),
                updatedAt = task.updatedAt.toString(),
            )
            queries.lastInsertRowId().executeAsOne()
        }
    }

    override suspend fun updateTask(task: Task): Boolean = withContext(Dispatchers.Default) {
        queries.update(
            title = task.title,
            description = task.description,
            isCompleted = task.isCompleted,
            updatedAt = task.updatedAt.toString(),
            id = task.id,
        )
        true
    }

    override suspend fun deleteTask(id: Long): Boolean = withContext(Dispatchers.Default) {
        queries.delete(id)
        true
    }
}
