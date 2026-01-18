package com.example.kotlin_cleanarchitecture_todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.DeleteTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.GetAllTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.InsertTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(
    private val getAllTodoUseCase: GetAllTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
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

    fun editTodo(todo: Todo, newTitle: String, newDescription: String) {
        if (newTitle.isBlank()) return
        viewModelScope.launch {
            val updatedTodo = todo.copy(
                title = newTitle,
                description = newDescription
            )
            updateTodoUseCase(updatedTodo)
        }
    }

    fun toggleComplete(todo: Todo){
        viewModelScope.launch {
            val updateTodo = todo.copy(isCompleted = !todo.isCompleted)
            updateTodoUseCase(updateTodo)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch {
            deleteTodoUseCase(todo)
        }
    }
}