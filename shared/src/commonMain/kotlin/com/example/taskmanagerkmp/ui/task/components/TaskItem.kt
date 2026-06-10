package com.example.taskmanagerkmp.ui.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanagerkmp.data.model.Task

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onToggle: () -> Unit,
    showDeleteDialog: (Boolean) -> Unit
) {

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Box(
            modifier = Modifier.size(22.dp)
                .clip(CircleShape)
                .border(
                    width = 1.5.dp,
                    color = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
                .background(if (task.isCompleted) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable(onClick = onToggle),
            contentAlignment = Alignment.Center

        ) {

            if (task.isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                color = if (task.isCompleted) MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.onSurface,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (task.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            StatusBadge(isCompleted = task.isCompleted)

        }

        IconButton(
            onClick = { showDeleteDialog(true) },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Deletar",
                tint = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }

    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        thickness = 0.5.dp
    )
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskItem(
        task = Task(
            id = 1,
            title = "Comprar leite",
            description = "Lembrar de comprar leite no mercado",
            isCompleted = false,
            createdAt = "",
            updatedAt = ""
        ),
        onClick = {},
        onToggle = {},
        showDeleteDialog = {}
    )
}