package com.example.taskmanagerkmp.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanagerkmp.data.repository.ITaskRepository
import com.example.taskmanagerkmp.data.repository.TaskRepository
import com.example.taskmanagerkmp.domain.usecase.AddTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.DeleteTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.GetTaskByIdUseCase
import com.example.taskmanagerkmp.domain.usecase.GetTasksUseCase
import com.example.taskmanagerkmp.domain.usecase.ToggleTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.UpdateTaskUseCase
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import org.koin.dsl.module

val appModule = module {

    single<ITaskRepository> { TaskRepository(get()) }
    // Use Cases
    factory { GetTasksUseCase(get()) }
    factory { AddTaskUseCase(get()) }
    factory { DeleteTaskUseCase(get()) }
    factory { ToggleTaskUseCase(get()) }
    factory { GetTaskByIdUseCase(get()) }
    factory { UpdateTaskUseCase(get()) }

    factory {
        TaskViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }

}