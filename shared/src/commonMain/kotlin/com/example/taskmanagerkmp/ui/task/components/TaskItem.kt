package com.example.taskmanagerkmp.ui.task.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TaskItem(
    task: com.example.taskmanagerkmp.data.model.Task,
    onClick: () -> Unit,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(
                text = task.title,
                style = if (task.isCompleted)
                    MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.outline
                    )
                else MaterialTheme.typography.bodyLarge
            )
        },
        supportingContent = {
            if (task.description.isNotBlank())
                Text(task.description, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        leadingContent = {
            Checkbox(checked = task.isCompleted, onCheckedChange = { onToggle() })
        },
        trailingContent = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Deletar")
            }
        }
    )
    HorizontalDivider()
}