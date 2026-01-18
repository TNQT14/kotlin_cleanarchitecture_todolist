package com.example.kotlin_cleanarchitecture_todolist.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import com.example.kotlin_cleanarchitecture_todolist.presentation.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(viewModel: TodoViewModel) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Todo list")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todos) { todo ->
                TodoItem(
                    todo = todo,
                    onToggleComplete = {viewModel.toggleComplete(it)},
                    onDelete = {
                    viewModel.deleteTodo(todo)
                })
            }
        }
    }

    if (showAddDialog) {
        AddTodoDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, description ->
                viewModel.addTodo(title, description)
                showAddDialog = false
            }

        )
    }
}

@Composable
fun TodoItem(todo: Todo, onToggleComplete: (Todo) -> Unit, onDelete: (Todo) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = {
                    onToggleComplete(todo)
                }
            )
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium
                )
                if (todo.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Button(
                onClick = { onDelete(todo) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }

        }
    }
}