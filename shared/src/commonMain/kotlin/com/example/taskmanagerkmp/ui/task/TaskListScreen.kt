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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import com.example.taskmanagerkmp.ui.task.components.AppTopBar
import com.example.taskmanagerkmp.ui.task.components.DeleteConfirmationDialog
import com.example.taskmanagerkmp.ui.task.components.TaskItem

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onTaskClick: (Task) -> Unit,
    onAddClick: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            AppTopBar("Minhas Tarefas")
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

//                val pendingTasks = remember {mutableStateOf(uiState.tasks.filter { !it.isCompleted })}
//                val completedTasks = remember {mutableStateOf(uiState.tasks.filter { it.isCompleted})}
                var showDeletedDialog by rememberSaveable{ mutableStateOf(false) }



                LazyColumn(contentPadding = padding) {

                  //  if(pendingTasks.value.isNotEmpty()){
                        item {
                            Text(
                                text = "To do list",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                 //   }
                    items(uiState.tasks, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            onClick = { onTaskClick(task) },
                            onToggle = { viewModel.toggleTask(task) },
                            showDeleteDialog = { showDeletedDialog = it },
                        )

                        if(showDeletedDialog){
                            DeleteConfirmationDialog(
                                onConfirm = {viewModel.deleteTask(task.id)},
                                onDismiss = {showDeletedDialog = false}
                            )
                        }

                    }

//                    if(completedTasks.value.isNotEmpty()){
//                        item {
//                            Text(
//                                text = "Concluídas",
//                                style = MaterialTheme.typography.labelMedium,
//                                color = MaterialTheme.colorScheme.outline,
//                                modifier = Modifier.padding(horizontal = 16.dp, vertical= 8.dp)
//                            )
//                        }
//                        items(completedTasks.value, key = { it.id }) { task ->
//                            TaskItem(
//                                task = task,
//                                onClick = { onTaskClick(task) },
//                                onToggle = { viewModel.onEvent(TaskEvent.ToggleTask(task)) },
//                                showDeleteDialog = { showDeletedDialog = it },
//                            )
//                            }
//                        }
                }
            }
        }

        uiState.error?.let { error ->
            Snackbar(
                modifier = Modifier.padding(padding),
                action = {
                    TextButton(onClick = { }) {
                        Text("OK")
                    }
                }
            ) { Text(error) }
        }
    }
}
