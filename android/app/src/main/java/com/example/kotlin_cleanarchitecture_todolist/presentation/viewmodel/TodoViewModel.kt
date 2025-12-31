package com.example.kotlin_cleanarchitecture_todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.GetAllTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.InsertTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(
    private val getAllTodoUseCase: GetAllTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase
) : ViewModel( ){
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = getAllTodoUseCase().stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTodo(title: String, description: String = "") {
        if(title.isBlank()) return
        viewModelScope.launch {
            val newTodo = Todo(
                title = title,
                description = description,
                isCompleted = false,
                createdAt = System.currentTimeMillis()
            )
            insertTodoUseCase(newTodo)
        }

    }
}