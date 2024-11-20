package com.abisoft.todocompose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.abisoft.todocompose.R
@Composable
fun CustomCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isImportant: String
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .padding(2.dp)
            .background(
                color = if (isChecked) Color.Green else if (isImportant != "low") colorResource(
                    R.color.Color_gray_light
                ) else colorResource(R.color.white),
                shape = MaterialTheme.shapes.extraSmall
            )
            .border(
                width = 2.dp,
                color = if (isChecked) Color.Green else if (isImportant == "important") Color.Red else Color.Gray,
                shape = MaterialTheme.shapes.extraSmall
            )
            .clickable { onCheckedChange(!isChecked) },  // Here we flip the state
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
