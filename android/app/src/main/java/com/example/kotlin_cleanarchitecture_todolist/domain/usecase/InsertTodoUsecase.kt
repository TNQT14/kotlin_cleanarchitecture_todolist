package com.example.kotlin_cleanarchitecture_todolist.domain.usecase

import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import com.example.kotlin_cleanarchitecture_todolist.domain.repository.TodoRepository

class InsertTodoUseCase(
    private val repository: TodoRepository
){
    suspend operator fun invoke(todo: Todo):Long {
        return repository.insertTodo(todo)
    }
}