package com.example.taskmanagerkmp.data.domain.usecases


import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repositoryTest.TaskRepositoryFake
import com.example.taskmanagerkmp.domain.usecase.DeleteTaskUseCase
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteTaskUseCaseTest {

    private val repository = TaskRepositoryFake()
    private val useCase = DeleteTaskUseCase(repository)
    private val now = TimeZone.currentSystemDefault().toString()

    @Test
    fun `deve deletar tarefa existente`() = runTest {
        val task = Task(id = 1, title = "Tarefa", createdAt = now, updatedAt = now)
        repository.givenTasks(task)

        useCase(1L)

        assertEquals(0, repository.currentTasks().size)
    }

    @Test
    fun `nao deve afetar outras tarefas ao deletar`() = runTest {
        val task1 = Task(id = 1, title = "Tarefa 1", createdAt = now, updatedAt = now)
        val task2 = Task(id = 2, title = "Tarefa 2", createdAt = now, updatedAt = now)
        repository.givenTasks(task1, task2)

        useCase(1L)

        assertEquals(1, repository.currentTasks().size)
        assertEquals("Tarefa 2", repository.currentTasks().first().title)
    }
}