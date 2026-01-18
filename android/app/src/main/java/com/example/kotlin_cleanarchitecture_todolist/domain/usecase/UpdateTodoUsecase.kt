package com.example.kotlin_cleanarchitecture_todolist.domain.usecase

import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import com.example.kotlin_cleanarchitecture_todolist.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class UpdateTodoUseCase (
    private val repository: TodoRepository
){
    suspend operator fun invoke(todo: Todo){
        return repository.updateTodo(todo)
    }
}
