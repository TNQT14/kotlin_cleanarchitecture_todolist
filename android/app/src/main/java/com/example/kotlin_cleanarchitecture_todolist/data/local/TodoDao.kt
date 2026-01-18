package com.example.kotlin_cleanarchitecture_todolist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao{
    @Query("SELECT * FROM todos ORDER BY createdAt DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long

    @Update
    suspend fun updateTodo(todo: TodoEntity) :Unit

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
}