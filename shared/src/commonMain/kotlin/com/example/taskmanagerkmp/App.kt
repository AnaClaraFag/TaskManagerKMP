package com.example.taskmanagerkmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanagerkmp.data.local.DatabaseDriverFactory
import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.data.repository.TaskRepositoryImpl
import com.example.taskmanagerkmp.domain.usecase.AddTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.DeleteTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.GetTasksUseCase
import com.example.taskmanagerkmp.domain.usecase.ToggleTaskUseCase
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import com.example.taskmanagerkmp.ui.theme.TaskManagerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    TaskManagerTheme {
        // Composition root: build the dependency graph once and host the ViewModel.
        val repository = remember(driverFactory) { TaskRepositoryImpl(driverFactory) }
        val viewModel = viewModel {
            TaskViewModel(
                getTasks = GetTasksUseCase(repository),
                addTask = AddTaskUseCase(repository),
                toggleTask = ToggleTaskUseCase(repository),
                deleteTask = DeleteTaskUseCase(repository),
            )
        }
        val state by viewModel.state.collectAsStateWithLifecycle()

        Scaffold(
            topBar = { TopAppBar(title = { Text("Tasks") }) },
        ) { innerPadding ->
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                AddTaskBar(onAdd = viewModel::add)

                state.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }

                if (state.tasks.isEmpty() && !state.isLoading) {
                    Text(
                        text = "No tasks yet. Add one above.",
                        modifier = Modifier.padding(16.dp),
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                ) {
                    items(state.tasks, key = { it.id }) { task ->
                        TaskRow(
                            task = task,
                            onToggle = { viewModel.toggle(task) },
                            onDelete = { viewModel.delete(task.id) },
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun AddTaskBar(onAdd: (title: String, description: String) -> Unit) {
    var title by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("New task") },
            singleLine = true,
            modifier = Modifier.weight(1f),
        )
        Button(
            onClick = {
                onAdd(title, "")
                title = ""
            },
            enabled = title.isNotBlank(),
        ) {
            Text("Add")
        }
    }
}

@Composable
private fun TaskRow(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = task.isCompleted, onCheckedChange = { onToggle() })
        Text(
            text = task.title,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
        )
        TextButton(onClick = onDelete) {
            Text("Delete")
        }
    }
}
