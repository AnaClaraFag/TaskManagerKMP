package com.example.taskmanagerkmp.ui.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanagerkmp.data.model.Task
import com.example.taskmanagerkmp.ui.task.components.AppTopBar

@Composable
fun TaskDetailScreen(
    task: Task,
    onEdit: () -> Unit,
    onBack: () -> Unit,
    showDeleteDialog: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detalhes",
                onBack = onBack,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Hero section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                StatusBadge(isCompleted = task.isCompleted)
            }

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Descrição (só exibe se tiver conteúdo)
            if (task.description.isNotBlank()) {
                HorizontalDivider()
                DetailRow("Descrição", task.description)
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

            DetailRow("Criada em", value = task.createdAt)
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            DetailRow("Última atualização", value = task.updatedAt)
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Spacer(Modifier.weight(1f))

            ActionButtons(
                onEdit = onEdit,
                showDeleteDialog = { showDeleteDialog(it) }
            )

        }
    }
}

@Composable
fun ActionButtons(
    onEdit: () -> Unit,
    showDeleteDialog: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onEdit,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Editar tarefa")
        }

        OutlinedButton(
            onClick = { showDeleteDialog(true) },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(
                    MaterialTheme.colorScheme.error
                )
            )
        ) {
            Icon(Icons.Default.Delete, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Excluir tarefa")
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
                text = label,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun TaskDetailScreenPreview() {
    val task = Task(
        id = 1,
        title = "Comprar mantimentos",
        description = "Leite, pão, ovos e frutas",
        isCompleted = false,
        createdAt = "2024-06-01T10:00:00Z",
        updatedAt = "2024-06-02T15:30:00Z"
    )

    TaskDetailScreen(
        task = task,
        onEdit = {},
        onBack = {},
        showDeleteDialog = {}
    )
}