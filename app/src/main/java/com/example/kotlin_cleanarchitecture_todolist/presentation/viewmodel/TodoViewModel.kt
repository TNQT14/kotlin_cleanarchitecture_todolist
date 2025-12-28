package com.example.kotlin_cleanarchitecture_todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel : ViewModel( ){
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    fun addTodo(title: String, description: String = "") {
        if(title.isBlank()) return
        val newTodo = Todo(
            id = System.currentTimeMillis(),
            title = title,
            description = description,
            isCompleted = false,
            createdAt = System.currentTimeMillis()
        )
        
        _todos.value = _todos.value + newTodo

    }
}