@file:Suppress("DEPRECATION")

package com.abisoft.todocompose.utils

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.abisoft.todocompose.R
import com.abisoft.todocompose.TodoItemsRepository
import com.abisoft.todocompose.model.ItemData
import com.example.myapplication.okhttp_client.ApiClient
import com.example.myapplication.okhttp_client.TodoDto

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoItem(task: TodoDto, onCheckedChange: (Boolean) -> Unit, onDelete: () -> Unit) {
    val dismissState = rememberDismissState(DismissValue.Default)
    var todoItemsRepository = TodoItemsRepository(ApiClient.create())
    SwipeToDismiss(
        state = dismissState,
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        dismissContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.back_primary))
            ) {
                Spacer(modifier = Modifier.width(8.dp))

                CustomCheckbox(
                    isChecked = task.done==true,
                    onCheckedChange = onCheckedChange,
                    isImportant = task.importance.toString()
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = task.text.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (task.done==true) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.property_1_info_outline),
                        contentDescription = "Task Info",
                        modifier = Modifier.size(24.dp),
                        tint = colorResource(R.color.Color_gray)
                    )
                }
            }
        },
        dismissThresholds = { FractionalThreshold(0.5f) }, // 50% swipe qilinganda itemni o'chirish
        directions = setOf(DismissDirection.EndToStart)
    )

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        Log.d("DeleteTask", "Dismissed: Task deleted")
        todoItemsRepository.deleteTodo("Earendil", task.id.toString())
        onDelete()
    }

}
