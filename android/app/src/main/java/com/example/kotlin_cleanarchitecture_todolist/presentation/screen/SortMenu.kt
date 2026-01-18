package com.example.kotlin_cleanarchitecture_todolist.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.kotlin_cleanarchitecture_todolist.domain.model.SortType

@Composable
fun SortMenu(
    currentSort: SortType,
    onSortSelected: (SortType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    IconButton(onClick = { expanded = true }, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Sort"
        )
    }
    
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Ngày mới nhất") },
            onClick = {
                onSortSelected(SortType.DATE_NEWEST)
                expanded = false
            },
            leadingIcon = {
                if (currentSort == SortType.DATE_NEWEST) {
                    Icon(Icons.Default.Check, contentDescription = null)
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Ngày cũ nhất") },
            onClick = {
                onSortSelected(SortType.DATE_OLDEST)
                expanded = false
            },
            leadingIcon = {
                if (currentSort == SortType.DATE_OLDEST) {
                    Icon(Icons.Default.Check, contentDescription = null)
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Tiêu đề A-Z") },
            onClick = {
                onSortSelected(SortType.TITLE_AZ)
                expanded = false
            },
            leadingIcon = {
                if (currentSort == SortType.TITLE_AZ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                }
            }
        )
        DropdownMenuItem(
            text = { Text("Tiêu đề Z-A") },
            onClick = {
                onSortSelected(SortType.TITLE_ZA)
                expanded = false
            },
            leadingIcon = {
                if (currentSort == SortType.TITLE_ZA) {
                    Icon(Icons.Default.Check, contentDescription = null)
                }
            }
        )
    }
}
