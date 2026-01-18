package com.example.kotlin_cleanarchitecture_todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_cleanarchitecture_todolist.presentation.screen.TodoListScreen
import com.example.kotlin_cleanarchitecture_todolist.presentation.viewmodel.TodoViewModel
import com.example.kotlin_cleanarchitecture_todolist.ui.theme.Kotlin_cleanarchitecture_todolistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppModule.provideDatabase(this)
        val todoDao = AppModule.provideTodoDao(database)
        val repository = AppModule.provideRepository(todoDao)
        val getAllTodoUseCase = AppModule.providerGetAllTodoUseCase(repository)
        val insertTodoUseCase = AppModule.provideInsertTodoUsecase(repository)
        val updateTodoUseCase = AppModule.provideUpdateTodoUsecase(repository)
        val deleteTodoUseCase = AppModule.provideDeleteTodoUseCase(repository)

        enableEdgeToEdge()
        setContent {
            Kotlin_cleanarchitecture_todolistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: TodoViewModel = viewModel(
                        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                                @Suppress("UNCHECKED_CAST")
                                return TodoViewModel(
                                    getAllTodoUseCase,
                                    insertTodoUseCase,
                                    updateTodoUseCase,
                                    deleteTodoUseCase,
                                ) as T
                            }
                        }
                    )
                    TodoListScreen(viewModel = viewModel)
                }
            }
        }
    }
}