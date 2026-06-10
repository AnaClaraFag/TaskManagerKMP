package com.example.taskmanagerkmp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary          = Purple40,
    onPrimary        = Color(0xFFFFFFFF),
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,

    secondary        = PurpleGrey40,
    onSecondary      = Color(0xFFFFFFFF),
    secondaryContainer = PurpleGrey90,
    onSecondaryContainer = Color(0xFF1D192B),

    background       = Background,
    onBackground     = Color(0xFF1C1B1F),

    surface          = Surface,
    onSurface        = Color(0xFF1C1B1F),
    surfaceVariant   = SurfaceVar,
    onSurfaceVariant = Color(0xFF49454F),

    outline          = Color(0xFF79747E),
    outlineVariant   = Color(0xFFCAC4D0),

    error            = Red40,
    onError          = Color(0xFFFFFFFF),
    errorContainer   = Red90,
    onErrorContainer = Color(0xFF410002),
)

@Composable
fun TaskManagerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = AppTypography,
        shapes      = AppShapes,
        content     = content
    )
}