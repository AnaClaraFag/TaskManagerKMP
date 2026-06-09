package com.example.taskmanagerkmp.data.domain.usecases

import com.example.taskmanagerkmp.data.repositoryTest.TaskRepositoryFake
import com.example.taskmanagerkmp.domain.usecase.AddTaskUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AddTaskUseCaseTest {

    private val repository = TaskRepositoryFake()
    private val useCase = AddTaskUseCase(repository)

    @Test
    fun `deve criar tarefa com titulo e descricao validos`() = runTest {
        useCase("Comprar leite", "2 litros")
        assertEquals(1, repository.currentTasks().size)
        assertEquals("Comprar leite", repository.currentTasks().first().title)
    }

    @Test
    fun `deve lancar excecao quando titulo for vazio`() = runTest {
        assertFailsWith<IllegalArgumentException> {
            useCase("", "descrição")
        }
    }

    @Test
    fun `deve lancar excecao quando titulo for apenas espacos`() = runTest {
        assertFailsWith<IllegalArgumentException> {
            useCase("   ", "descrição")
        }
    }

    @Test
    fun `deve fazer trim no titulo`() = runTest {
        useCase("  Tarefa com espaços  ", "")
        assertEquals("Tarefa com espaços", repository.currentTasks().first().title)
    }
}