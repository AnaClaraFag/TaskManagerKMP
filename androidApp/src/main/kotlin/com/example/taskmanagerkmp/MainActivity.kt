class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = remember {
                TaskViewModel(
                    getTasksUseCase   = GetTasksUseCase(TaskRepositoryImpl(DatabaseDriverFactory(this))),
                    createTaskUseCase = CreateTaskUseCase(TaskRepositoryImpl(DatabaseDriverFactory(this))),
                    updateTaskUseCase = UpdateTaskUseCase(TaskRepositoryImpl(DatabaseDriverFactory(this))),
                    deleteTaskUseCase = DeleteTaskUseCase(TaskRepositoryImpl(DatabaseDriverFactory(this))),
                    toggleTaskUseCase = ToggleTaskUseCase(TaskRepositoryImpl(DatabaseDriverFactory(this)))
                )
            }
            TaskManagerKMPTheme {
                AppNavigation(viewModel)
            }
        }
    }
}