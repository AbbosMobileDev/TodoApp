package com.abisoft.todocompose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenu
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ImportanceSelection() {
    var isImportant by remember { mutableStateOf(false) }
    var selectedImportance by remember { mutableStateOf("Нет") }
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = "Важность", modifier = Modifier.weight(1f))


        Text(
            text = selectedImportance,
            color = Color.Gray,
            modifier = Modifier
                .clickable { expanded = !expanded }
        )
    }


    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(onClick = {
            selectedImportance = "Нет"
            expanded = false
            isImportant = false
        }) {
            Text("Нет")
        }
        DropdownMenuItem(onClick = {
            selectedImportance = "Низкий"
            expanded = false
            isImportant = true
        }) {
            Text("Низкий")
        }
        DropdownMenuItem(onClick = {
            selectedImportance = "!! Высокий"
            expanded = false
            isImportant = true
        }) {
            Text("!! Высокий")
        }
    }
}
