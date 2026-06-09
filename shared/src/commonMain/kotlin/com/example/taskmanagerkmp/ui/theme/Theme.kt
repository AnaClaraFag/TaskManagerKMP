package com.example.taskmanagerkmp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TaskManagerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary        = Color(0xFF6650A4),
            secondary      = Color(0xFF625B71),
            tertiary       = Color(0xFF7D5260),
            background     = Color(0xFFFFFBFE),
            surface        = Color(0xFFFFFBFE),
            onPrimary      = Color(0xFFFFFFFF),
            onBackground   = Color(0xFF1C1B1F),
            onSurface      = Color(0xFF1C1B1F),
        ),
        typography = Typography(),
        content    = content
    )
}