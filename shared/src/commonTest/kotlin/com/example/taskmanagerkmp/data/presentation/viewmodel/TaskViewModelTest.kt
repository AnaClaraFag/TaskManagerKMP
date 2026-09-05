package com.example.taskmanagerkmp.data.presentation.viewmodel

import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repositoryTest.ITaskRepositoryFake
import com.example.taskmanagerkmp.domain.usecase.*
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import kotlinx.datetime.TimeZone
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository = ITaskRepositoryFake()
    private val now = TimeZone.currentSystemDefault().toString()

    private fun buildViewModel() = TaskViewModel(
        getTasksUseCase = GetTasksUseCase(repository),
        addTaskUseCase = AddTaskUseCase(repository),
        toggleTaskUseCase = ToggleTaskUseCase(repository),
        deleteTaskUseCase = DeleteTaskUseCase(repository),
        getTaskByIdUseCase = GetTaskByIdUseCase(repository),
        updateTaskUseCase = UpdateTaskUseCase(repository)
    )

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `estado inicial deve ter lista vazia e isLoading false`() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        val state = viewModel.state.first()

        assertFalse(state.isLoading)
        assertTrue(state.tasks.isEmpty())
    }

    @Test
    fun `CreateTask deve adicionar tarefa na lista`() = runTest(testDispatcher) {
        val viewModel = buildViewModel()
        viewModel.createTask("Nova tarefa", "Descrição")

        advanceUntilIdle()

        viewModel.refresh()

        val state = viewModel.state.value

        // Log após o evento
        println("Estado após evento: ${viewModel.state.value}")
        println("Tasks no repo depois: ${repository.currentTasks()}")

        assertEquals(1, state.tasks.size)
        assertEquals("Nova tarefa", state.tasks.first().title)
    }

    @Test
    fun `CreateTask com titulo vazio nao deve adicionar tarefa`() = runTest {
        val viewModel = buildViewModel()
        viewModel.createTask("", "")
        advanceUntilIdle()

        val state = viewModel.state.first()
        assertTrue(state.tasks.isEmpty())
    }

    @Test
    fun `DeleteTask deve remover tarefa da lista`() = runTest {
        val task = Task(id = 1, title = "Tarefa", createdAt = now, updatedAt = now)
        repository.givenTasks(task)

        val viewModel = buildViewModel()
        advanceUntilIdle()

        viewModel.deleteTask(1L)
        advanceUntilIdle()

        val state = viewModel.state.first()
        assertTrue(state.tasks.isEmpty())
    }

    @Test
    fun `ToggleTask deve inverter isCompleted`() = runTest {
        val task = Task(id = 1, title = "Tarefa", isCompleted = false, createdAt = now, updatedAt = now)
        repository.givenTasks(task)

        val viewModel = buildViewModel()
        advanceUntilIdle()

        viewModel.toggleTask(task)
        advanceUntilIdle()

        val state = viewModel.state.first()
        assertTrue(state.tasks.first().isCompleted)
    }

}