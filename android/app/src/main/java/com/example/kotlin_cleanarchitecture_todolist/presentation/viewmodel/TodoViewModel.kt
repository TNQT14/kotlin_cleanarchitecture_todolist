package com.example.kotlin_cleanarchitecture_todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_cleanarchitecture_todolist.domain.model.FilterType
import com.example.kotlin_cleanarchitecture_todolist.domain.model.SortType
import com.example.kotlin_cleanarchitecture_todolist.domain.model.Todo
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.DeleteTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.GetAllTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.InsertTodoUseCase
import com.example.kotlin_cleanarchitecture_todolist.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(
    private val getAllTodoUseCase: GetAllTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel(){
    private val allTodos: StateFlow<List<Todo>> = getAllTodoUseCase().stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    private val _filterType = MutableStateFlow(FilterType.ALL)
    val filterType: StateFlow<FilterType> = _filterType.asStateFlow()
    
    private val _sortType = MutableStateFlow(SortType.DATE_NEWEST)
    val sortType: StateFlow<SortType> = _sortType.asStateFlow()
    
    val todos: StateFlow<List<Todo>> = combine(
        allTodos,
        _filterType,
        _sortType
    ) { todos, filter, sort ->
        var result = todos

        result = when (filter) {
            FilterType.ALL -> result
            FilterType.ACTIVE -> result.filter { !it.isCompleted }
            FilterType.COMPLETED -> result.filter { it.isCompleted }
        }

        result = when (sort) {
            SortType.DATE_NEWEST -> result.sortedByDescending { it.createdAt }
            SortType.DATE_OLDEST -> result.sortedBy { it.createdAt }
            SortType.TITLE_AZ -> result.sortedBy { it.title.lowercase() }
            SortType.TITLE_ZA -> result.sortedByDescending { it.title.lowercase() }
        }
        
        result
    }.stateIn(
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
    
    fun setFilterType(filterType: FilterType) {
        _filterType.value = filterType
    }
    
    fun setSortType(sortType: SortType) {
        _sortType.value = sortType
    }
}