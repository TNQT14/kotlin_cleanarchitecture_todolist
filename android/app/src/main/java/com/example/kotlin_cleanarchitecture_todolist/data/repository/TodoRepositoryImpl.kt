package com.example.kotlin_cleanarchitecture_todolist.data.repository

import com.example.kotlin_cleanarchitecture_todolist.data.local.TodoDao
import com.example.kotlin_cleanarchitecture_todolist.data.local.TodoEntity
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import com.example.kotlin_cleanarchitecture_todolist.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl (
    private val todoDao: TodoDao
): TodoRepository{
    override fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTodoById(id: Long): Todo? {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodo(todo: Todo): Long {
        return todoDao.insertTodo(TodoEntity.fromDomain(todo))
    }

    override suspend fun updateTodo(todo: Todo) {
        return todoDao.updateTodo(TodoEntity.fromDomain(todo))
    }

    override suspend fun deleteTodo(todo: Todo) {
        return todoDao.deleteTodo(TodoEntity.fromDomain(todo))
    }
}
