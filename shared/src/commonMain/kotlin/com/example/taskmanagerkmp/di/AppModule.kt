package com.example.taskmanagerkmp.di

import com.example.taskmanagerkmp.data.repository.TaskRepository
import com.example.taskmanagerkmp.data.repository.TaskRepositoryImpl
import com.example.taskmanagerkmp.domain.usecase.AddTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.DeleteTaskUseCase
import com.example.taskmanagerkmp.domain.usecase.GetTasksUseCase
import com.example.taskmanagerkmp.domain.usecase.ToggleTaskUseCase
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import org.koin.dsl.module

val appModule = module{

    single < TaskRepository> { TaskRepositoryImpl(get()) }

    // Use Cases
    factory { GetTasksUseCase(get()) }
    factory { AddTaskUseCase(get()) }
    factory { DeleteTaskUseCase(get()) }
    factory { ToggleTaskUseCase(get()) }

    factory {
        TaskViewModel(
            getTasks = get(),
            addTask = get(),
            toggleTask = get(),
            deleteTask = get()
        )
    }

}