package com.example.taskmanagerkmp.presentation.viewmodel

import com.example.taskmanagerkmp.data.model.Task

sealed class TaskEvent {

    // Carrega dados — envia title e description como primitivos
    // (não a Task inteira, pois ela ainda não existe)
    data class CreateTask(
        val title: String,
        val description: String
    ) : TaskEvent()

    // Edição — envia a Task completa já modificada pela UI
    data class UpdateTask(val task: Task) : TaskEvent()

    // Deleção — só precisa do id, não da Task inteira
    data class DeleteTask(val id: Long) : TaskEvent()

    // Toggle concluída/pendente — envia a Task para o ViewModel
    // inverter o isCompleted internamente
    data class ToggleTask(val task: Task) : TaskEvent()

    // Seleciona tarefa para edição — null limpa a seleção
    data class SelectTask(val task: Task?) : TaskEvent()

    data class GetTaskById(val id: Long) : TaskEvent()

    // Limpa a mensagem de erro do UiState
    data object ClearError : TaskEvent()
}