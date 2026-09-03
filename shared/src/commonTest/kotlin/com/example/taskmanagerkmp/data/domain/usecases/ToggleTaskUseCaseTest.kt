package com.example.taskmanagerkmp.data.domain.usecases

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repositoryTest.ITaskRepositoryFake
import com.example.taskmanagerkmp.domain.usecase.ToggleTaskUseCase
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ToggleTaskUseCaseTest {

    private val repository = ITaskRepositoryFake()
    private val useCase =  ToggleTaskUseCase(repository)
    private val now = TimeZone.currentSystemDefault().toString()

    @Test
    fun `deve marcar tarefa pendente como concluida`() = runTest {
        val task = Task(id = 1, title = "Tarefa", isCompleted = false, createdAt = now, updatedAt = now)
        repository.givenTasks(task)

        useCase(task)

        assertTrue(repository.currentTasks().first().isCompleted)
    }

    @Test
    fun `deve marcar tarefa concluida como pendente`() = runTest {
        val task = Task(id = 1, title = "Tarefa", isCompleted = true, createdAt = now, updatedAt = now)
        repository.givenTasks(task)

        useCase(task)

        assertFalse(repository.currentTasks().first().isCompleted)
    }

    @Test
    fun `deve atualizar updatedAt ao fazer toggle`() = runTest {
        val task = Task(id = 1, title = "Tarefa", isCompleted = false, createdAt = now, updatedAt = now)
        repository.givenTasks(task)

        useCase(task)

        val updated = repository.currentTasks().first()
        assertTrue(updated.updatedAt >= now)
    }
}