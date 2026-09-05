package com.example.taskmanagerkmp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.domain.usecase.AddTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.DeleteTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.GetTaskByIdUseCase
import com.example.taskmanagerkmp.domain.usecase.GetTasksUseCase
import com.example.taskmanagerkmp.domain.usecase.ToggleTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val toggleTaskUseCase: ToggleTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    data class UiState(
        val tasks: List<Task> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val selectedTask: Task? = null

    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching { getTasksUseCase.invoke() }
                .onSuccess { tasks ->
                    _state.update {
                        it.copy(
                            tasks = tasks,
                            isLoading = false
                        )
                    }
                }
                .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun createTask(title: String, description: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            runCatching { addTaskUseCase(title, description) }
                .onSuccess { refresh() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            runCatching { toggleTaskUseCase(task) }
                .onSuccess { refresh() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            runCatching { deleteTaskUseCase(id) }
                .onSuccess { refresh() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
        }
    }

    fun getTaskById(id: Long) {
        viewModelScope.launch {
            runCatching { getTaskByIdUseCase(id) }
                .onSuccess { task -> _state.update { it.copy(selectedTask = task) } }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
        }
    }


    fun updateTask(task: Task) {
        viewModelScope.launch {
            runCatching { updateTaskUseCase(task) }
                .onSuccess { _state.update { it.copy() } }
        }
    }
}
