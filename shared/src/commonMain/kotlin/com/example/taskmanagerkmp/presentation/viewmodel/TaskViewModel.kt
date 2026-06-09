package com.example.taskmanagerkmp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.domain.usecase.AddTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.DeleteTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.GetTasksUseCase
import com.example.taskmanagerkmp.domain.usecase.ToggleTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val getTasks: GetTasksUseCase,
    private val addTask: AddTaskUseCase,
    private val toggleTask: ToggleTaskUseCase,
    private val deleteTask: DeleteTaskUseCase,
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
            runCatching { getTasks() }
                .onSuccess { tasks -> _state.update { it.copy(tasks = tasks, isLoading = false) } }
                .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun add(title: String, description: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            runCatching { addTask(title, description) }
                .onSuccess { refresh() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
        }
    }

    fun toggle(task: Task) {
        viewModelScope.launch {
            runCatching { toggleTask(task) }
                .onSuccess { refresh() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            runCatching { deleteTask(id) }
                .onSuccess { refresh() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
        }
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.CreateTask  -> add(event.title, event.description)
            is TaskEvent.UpdateTask  -> toggle(event.task) // adapte conforme seu UpdateUseCase
            is TaskEvent.DeleteTask  -> delete(event.id)
            is TaskEvent.ToggleTask  -> toggle(event.task)
            is TaskEvent.SelectTask  -> _state.update { it.copy(selectedTask = event.task) }
            is TaskEvent.ClearError  -> _state.update { it.copy(error = null) }
        }
    }
}
