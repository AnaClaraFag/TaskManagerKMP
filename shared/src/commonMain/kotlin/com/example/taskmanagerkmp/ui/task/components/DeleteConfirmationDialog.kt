package com.example.taskmanagerkmp.ui.task.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar Exclusão") },
        text = { Text("Tem certeza que deseja excluir esta tarefa?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
               Text("Sim")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Não")
            }
        }
    )
}