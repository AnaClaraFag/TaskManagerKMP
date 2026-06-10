package com.example.taskmanagerkmp.ui.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanagerkmp.ui.theme.DoneBg
import com.example.taskmanagerkmp.ui.theme.DoneText
import com.example.taskmanagerkmp.ui.theme.PendingBg
import com.example.taskmanagerkmp.ui.theme.PendingText


@Composable
fun StatusBadge(
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    val bg = if (isCompleted) DoneBg else PendingBg
    val textColor = if (isCompleted) DoneText else PendingText
    val label = if (isCompleted) "Done" else "Pending"

    Text(
        text = label,
        color = textColor,
        fontStyle = MaterialTheme.typography.labelMedium.fontStyle,
        modifier = modifier
            .background(bg, shape = MaterialTheme.shapes.extraSmall)
            .padding(horizontal = 8.dp, vertical = 4.dp)

    )
}

@Preview
@Composable
fun StatusBadgePreview() {
    StatusBadge(isCompleted = true)
}