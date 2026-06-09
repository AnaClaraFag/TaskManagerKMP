package com.example.taskmanagerkmp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read
import com.example.taskmanagerkmp.presentation.viewmodel.TaskEvent
import com.example.taskmanagerkmp.presentation.viewmodel.TaskViewModel
import com.example.taskmanagerkmp.ui.task.TaskFormScreen
import com.example.taskmanagerkmp.ui.task.TaskListScreen

sealed class Screen(val route: String) {
    data object TaskList   : Screen("task_list")
    data object TaskCreate : Screen("task_create")
    data class  TaskEdit(val id: Long) : Screen("task_edit/{taskId}") {
        companion object {
            const val ROUTE = "task_edit/{taskId}"
            fun createRoute(id: Long) = "task_edit/$id"
        }
    }
}

@Composable
fun AppNavigation(viewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {
        composable(Screen.TaskList.route) {
            TaskListScreen(
                viewModel   = viewModel,
                onTaskClick = { task ->
                    viewModel.onEvent(TaskEvent.SelectTask(task))
                    navController.navigate(Screen.TaskEdit.createRoute(task.id))
                },
                onAddClick  = {
                    viewModel.onEvent(TaskEvent.SelectTask(null))
                    navController.navigate(Screen.TaskCreate.route)
                }
            )
        }

        composable(Screen.TaskCreate.route) {
            TaskFormScreen(
                viewModel  = viewModel,
                taskToEdit = null,
                onSave     = { navController.popBackStack() },
                onCancel   = { navController.popBackStack() }
            )
        }

        composable(Screen.TaskEdit.ROUTE) { backStackEntry ->
            val taskId = backStackEntry.arguments?.read { getLongOrNull("taskId") }
            val uiState by viewModel.state.collectAsState()
            val task = uiState.tasks.find { it.id == taskId }

            TaskFormScreen(
                viewModel  = viewModel,
                taskToEdit = task,
                onSave     = { navController.popBackStack() },
                onCancel   = { navController.popBackStack() }
            )
        }
    }
}