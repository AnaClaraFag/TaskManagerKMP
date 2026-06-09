package com.example.taskmanagerkmp.data.model

import kotlinx.datetime.LocalDateTime

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: String,
    val updatedAt: String
)
