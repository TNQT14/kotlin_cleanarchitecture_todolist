package com.example.kotlin_cleanarchitecture_todolist.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlin_cleanarchitecture_todolist.domain.model.FilterType

@Composable
fun FilterChipRow(
    currentFilter: FilterType,
    onFilterSelected: (FilterType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = currentFilter == FilterType.ALL,
            onClick = { onFilterSelected(FilterType.ALL) },
            label = { Text("Tất cả") }
        )
        FilterChip(
            selected = currentFilter == FilterType.ACTIVE,
            onClick = { onFilterSelected(FilterType.ACTIVE) },
            label = { Text("Chưa hoàn thành") }
        )
        FilterChip(
            selected = currentFilter == FilterType.COMPLETED,
            onClick = { onFilterSelected(FilterType.COMPLETED) },
            label = { Text("Đã hoàn thành") }
        )
    }
}
