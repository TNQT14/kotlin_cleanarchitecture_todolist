package com.example.kotlin_cleanarchitecture_todolist.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    fun format(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }
    
    fun formatRelative(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < 60 * 1000 -> "Vừa xong"
            diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)} phút trước"
            diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)} giờ trước"
            diff < 7 * 24 * 60 * 60 * 1000 -> "${diff / (24 * 60 * 60 * 1000)} ngày trước"
            else -> format(timestamp)
        }
    }
}
