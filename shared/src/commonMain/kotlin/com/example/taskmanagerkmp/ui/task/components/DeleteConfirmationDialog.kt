package com.example.taskmanagerkmp.ui.task.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar Exclusão") },
        text = { Text("Tem certeza que deseja excluir esta tarefa?") },
        confirmButton = {
            Button(
                onClick = onConfirm,
            ) {
                Text(
                    text = "Excluir",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.surface,
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
            ) {
                Text(
                    text = "Cancelar",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black,
                    )
            }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(8.dp)
    )
}

@Preview
@Composable
fun DeleteDialogPreview() {
    DeleteConfirmationDialog(
        onConfirm = {},
        onDismiss = {}
    )
}