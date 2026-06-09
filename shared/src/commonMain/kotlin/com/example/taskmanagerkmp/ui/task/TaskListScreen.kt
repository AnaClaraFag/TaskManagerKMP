package com.example.taskmanagerkmp.ui.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.presentation.viewmodel.TaskEvent
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import com.example.taskmanagerkmp.ui.task.components.TaskItem
import org.koin.compose.koinInject

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = koinInject<TaskViewModel>(),
    sampleTasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onAddClick: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Minhas Tarefas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Nova tarefa")
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.tasks.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhuma tarefa ainda. Crie a primeira!")
                }
            }
            else -> {
                LazyColumn(contentPadding = padding) {
                    items(uiState.tasks, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            onClick = { onTaskClick(task) },
                            onToggle = { viewModel.onEvent(TaskEvent.ToggleTask(task)) },
                            onDelete = { viewModel.onEvent(TaskEvent.DeleteTask(task.id)) }
                        )
                    }
                }
            }
        }

        uiState.error?.let { error ->
            Snackbar(
                modifier = Modifier.padding(padding),
                action = {
                    TextButton(onClick = { viewModel.onEvent(TaskEvent.ClearError) }) {
                        Text("OK")
                    }
                }
            ) { Text(error) }
        }
    }
}

@Preview
@Composable
fun TaskListScreenPreview() {
    val sampleTasks = listOf(
        Task(
            id = 1,
            title = "Comprar leite",
            description = "Lembre-se de comprar leite no supermercado",
            isCompleted = false,
            createdAt = "2024-06-01T10:00:00Z",
            updatedAt = "2024-06-02T15:30:00Z"),
        Task(
            id = 2,
            title = "Enviar email",
            description = "Enviar email para o chefe sobre o projeto",
            isCompleted = true,
            createdAt = "2024-06-01T10:00:00Z",
            updatedAt = "2024-06-01T10:00:00Z"
    )
    )

    TaskListScreen(
        sampleTasks = sampleTasks,
        onTaskClick = {},
        onAddClick = {}
    )

}