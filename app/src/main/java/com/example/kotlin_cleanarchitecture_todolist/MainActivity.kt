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
        enableEdgeToEdge()
        setContent {
            Kotlin_cleanarchitecture_todolistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Tạo ViewModel và hiển thị màn hình TodoListScreen
                    val viewModel: TodoViewModel = viewModel()
                    TodoListScreen(viewModel = viewModel)
                }
            }
        }
    }
}