package com.abisoft.todocompose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abisoft.todocompose.R

@Composable
fun ToggleIcon(onVisibilityChange: (Boolean) -> Unit) {
    var isVisible by remember { mutableStateOf(true) }

    Icon(
        painter = painterResource(
            id = if (isVisible) R.drawable.visibility else R.drawable.property_1_visibility_off
        ),
        contentDescription = "Toggle visibility",
        modifier = Modifier
            .size(24.dp)
            .clickable { isVisible = !isVisible
                onVisibilityChange(isVisible)},
        tint = colorResource(R.color.Color_blue)
    )
}
