package com.example.kotlin_cleanarchitecture_todolist.domain.usecase

import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import com.example.kotlin_cleanarchitecture_todolist.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetAllTodoUseCase(
    private val repository: TodoRepository
){
    operator fun invoke(): Flow<List<Todo>> {
        return repository.getAllTodos()
    }
}