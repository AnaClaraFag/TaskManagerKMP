package com.example.taskmanagerkmp.ui.task

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanagerkmp.data.model.Task
import kotlinx.datetime.LocalDateTime

@Composable
fun TaskDetailScreen(
    task    : Task,
    onEdit  : () -> Unit,
    onBack  : () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhe da Tarefa") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector   = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector        = Icons.Default.Edit,
                            contentDescription = "Editar"
                        )
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

            // Status badge
            StatusBadge(isCompleted = task.isCompleted)

            HorizontalDivider()

            // Título
            SectionLabel("Título")
            Text(
                text  = task.title,
                style = MaterialTheme.typography.headlineSmall
            )

            // Descrição (só exibe se tiver conteúdo)
            if (task.description.isNotBlank()) {
                HorizontalDivider()
                SectionLabel("Descrição")
                Text(
                    text  = task.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            HorizontalDivider()

            // Datas
            SectionLabel("Criada em")
            Text(
                text  = task.createdAt.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )

            SectionLabel("Última atualização")
            Text(
                text  = task.updatedAt.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(Modifier.weight(1f))

            // Botão de edição no rodapé
            Button(
                onClick  = onEdit,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Editar Tarefa")
            }
        }
    }
}

@Composable
private fun StatusBadge(isCompleted: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val (label, color) = if (isCompleted)
            "Concluída" to MaterialTheme.colorScheme.primary
        else
            "Pendente" to MaterialTheme.colorScheme.secondary

        Surface(
            shape = MaterialTheme.shapes.small,
            color = color.copy(alpha = 0.12f),
            contentColor = color
        ) {
            Text(
                text     = label,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style    = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text  = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.outline
    )
}

@Preview
@Composable
fun TaskDetailScreenPreview() {
    val task = Task(
        id          = 1,
        title       = "Comprar mantimentos",
        description = "Leite, pão, ovos e frutas",
        isCompleted = false,
        createdAt   = "2024-06-01T10:00:00Z",
        updatedAt   = "2024-06-02T15:30:00Z"
    )

    TaskDetailScreen(
        task = task ,
        onEdit = {},
        onBack = {}
    )
}