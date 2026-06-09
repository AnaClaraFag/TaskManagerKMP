package com.example.taskmanagerkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.taskmanagerkmp.data.local.DatabaseDriverFactory
import com.example.taskmanagerkmp.data.repository.TaskRepositoryImpl
import com.example.taskmanagerkmp.domain.usecase.AddTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.DeleteTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.GetTasksUseCase
import com.example.taskmanagerkmp.domain.usecase.ToggleTaskUseCase
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import com.example.taskmanagerkmp.ui.navigation.AppNavigation
import com.example.taskmanagerkmp.ui.theme.TaskManagerTheme
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val repository = TaskRepositoryImpl(DatabaseDriverFactory())
    val viewModel  = TaskViewModel(
        getTasks    = GetTasksUseCase(repository),
        addTask     = AddTaskUseCase(repository),
        toggleTask  = ToggleTaskUseCase(repository),
        deleteTask  = DeleteTaskUseCase(repository)
    )
    return ComposeUIViewController {
        TaskManagerTheme {
            AppNavigation(viewModel)
        }
    }
}
