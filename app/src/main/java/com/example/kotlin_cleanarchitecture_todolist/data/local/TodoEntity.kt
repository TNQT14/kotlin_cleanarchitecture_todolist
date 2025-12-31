package com.example.kotlin_cleanarchitecture_todolist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Long = System.currentTimeMillis()
)
{
    fun toDomain(): Todo {
        return Todo(
            id = id,
            title =title,
            description = description,
            isCompleted = isCompleted,
            createdAt = createdAt
        )
    }

    companion object{
        fun fromDomain(todo:Todo): TodoEntity{
            return TodoEntity(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                isCompleted = todo.isCompleted,
                createdAt = todo.createdAt
            )
        }
    }
}