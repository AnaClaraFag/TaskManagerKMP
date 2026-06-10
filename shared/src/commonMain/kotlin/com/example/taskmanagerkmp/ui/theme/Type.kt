package com.example.taskmanagerkmp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    // Títulos de tela
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize   = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize   = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    // Título de item
    titleMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    // Corpo
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 13.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    // Labels (tags, seções)
    labelMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize   = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize   = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)