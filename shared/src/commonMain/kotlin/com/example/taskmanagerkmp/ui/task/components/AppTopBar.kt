package com.example.taskmanagerkmp.ui.task.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actionIcon: ImageVector? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        actions = {
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction) {
                    Text(
                        text = actionLabel,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else if (actionIcon != null && onAction != null) {
                IconButton(onClick = onAction) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionLabel,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview() {
    AppTopBar(
        "Detalhes",
        onBack = {},
        actionIcon = Icons.Default.Edit,
        actionLabel = "Editar",
        onAction = {}
    )
}