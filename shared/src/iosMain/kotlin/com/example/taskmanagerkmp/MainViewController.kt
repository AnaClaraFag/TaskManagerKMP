package com.example.taskmanagerkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.taskmanagerkmp.di.appModule
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import com.example.taskmanagerkmp.ui.navigation.AppNavigation
import com.example.taskmanagerkmp.ui.theme.TaskManagerTheme
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    startKoin {
        modules(platformModule, appModule)
    }
    return ComposeUIViewController {
        TaskManagerTheme {
            AppNavigation()
        }
    }
}
