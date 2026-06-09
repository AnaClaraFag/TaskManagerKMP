package com.example.taskmanagerkmp.ui.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.presentation.viewmodel.TaskEvent
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import kotlinx.datetime.LocalDateTime
import org.koin.compose.koinInject

@Composable
fun TaskFormScreen(
    viewModel: TaskViewModel = koinInject<TaskViewModel>(),
    taskToEdit : Task?,
    onSave     : () -> Unit,
    onCancel   : () -> Unit
) {
    val isEditing = taskToEdit != null

    var title       by remember { mutableStateOf(taskToEdit?.title       ?: "") }
    var description by remember { mutableStateOf(taskToEdit?.description ?: "") }
    var isCompleted by remember { mutableStateOf(taskToEdit?.isCompleted ?: false) }

    var titleError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar Tarefa" else "Nova Tarefa") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        if (title.isBlank()) {
                            titleError = true
                            return@TextButton
                        }
                        if (isEditing && taskToEdit != null) {
                            viewModel.onEvent(
                                TaskEvent.UpdateTask(
                                    taskToEdit.copy(
                                        title       = title.trim(),
                                        description = description.trim(),
                                        isCompleted = isCompleted
                                    )
                                )
                            )
                        } else {
                            viewModel.onEvent(
                                TaskEvent.CreateTask(
                                    title       = title.trim(),
                                    description = description.trim()
                                )
                            )
                        }
                        onSave()
                    }) {
                        Text("Salvar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Campo título
            OutlinedTextField(
                value         = title,
                onValueChange = {
                    title      = it
                    titleError = false
                },
                label         = { Text("Título *") },
                placeholder   = { Text("Ex: Comprar leite") },
                isError       = titleError,
                supportingText = {
                    if (titleError) Text(
                        "O título é obrigatório",
                        color = MaterialTheme.colorScheme.error
                    )
                },
                singleLine    = true,
                modifier      = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )

            // Campo descrição
            OutlinedTextField(
                value         = description,
                onValueChange = { description = it },
                label         = { Text("Descrição") },
                placeholder   = { Text("Detalhes opcionais...") },
                minLines      = 3,
                maxLines      = 6,
                modifier      = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction      = ImeAction.Done
                )
            )

            // Toggle concluída (só aparece na edição)
            if (isEditing) {
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tarefa concluída", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked         = isCompleted,
                        onCheckedChange = { isCompleted = it }
                    )
                }
                HorizontalDivider()
            }

            // Botão deletar (só na edição)
            if (isEditing && taskToEdit != null) {
                Spacer(Modifier.weight(1f))
                OutlinedButton(
                    onClick  = {
                        viewModel.onEvent(TaskEvent.DeleteTask(taskToEdit.id))
                        onSave()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors   = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border   = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Deletar tarefa")
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskFormScreenPreview() {
    TaskFormScreen(
        taskToEdit =
            Task(id = 1,
            title = "Comprar leite",
            description = "Lembre-se de pegar o desnatado",
            isCompleted = false,
                createdAt   = "2024-06-01T10:00:00Z",
                updatedAt   = "2024-06-02T15:30:00Z"),
        onSave     = {},
        onCancel   = {}
    )
}