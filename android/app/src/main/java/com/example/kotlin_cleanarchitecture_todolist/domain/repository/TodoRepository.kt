package com.example.kotlin_cleanarchitecture_todolist.domain.repository
import com.example.kotlin_cleanarchitecture_todolist.data.local.TodoEntity
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow
interface TodoRepository {
    fun getAllTodos(): Flow<List<Todo>>
    suspend fun getTodoById(id: Long): Todo?
    suspend fun insertTodo(todo: Todo): Long
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
}