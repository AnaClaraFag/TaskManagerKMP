package com.example.taskmanagerkmp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),   // tags, badges
    small      = RoundedCornerShape(8.dp),   // campos, botões pequenos
    medium     = RoundedCornerShape(12.dp),  // cards, FAB
    large      = RoundedCornerShape(16.dp),  // bottom sheets, dialogs
    extraLarge = RoundedCornerShape(24.dp)   // modais grandes
)