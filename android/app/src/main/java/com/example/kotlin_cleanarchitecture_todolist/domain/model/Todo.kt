package com.example.kotlin_cleanarchitecture_todolist.domain.model

data class Todo(
    val id: Long? = null,
    val title: String,
    val description: String  = "",
    var isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)