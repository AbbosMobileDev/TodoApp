package com.abisoft.todocompose

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abisoft.todocompose.model.Importance
import com.abisoft.todocompose.model.ItemData
import com.abisoft.todocompose.ui.theme.ToDoComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                val navController = rememberNavController()
                TodoScreen(navController)

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { TodoScreen(navController) }
                    composable("addTask") {
                        AddTaskScreen(onTaskAdded = {
                            navController.popBackStack()

                        }, onClose = {

                            navController.popBackStack()
                        })
                    }
                }
            }

        }
    }
}

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

@Composable
fun TodoScreen(navController: NavController) {
    var tasks by remember { mutableStateOf(TodoItemsRepository.getTasks()) }
    var completedTasksCount by remember { mutableStateOf(tasks.count { it.isCompleted }) }
    var isVisible by remember { mutableStateOf(true) }

    val visibleTasks = if (isVisible) tasks else tasks.filter { !it.isCompleted }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addTask") },
                shape = CircleShape,
                containerColor = colorResource(R.color.Color_blue)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = Color.White
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(colorResource(R.color.back_primary))
            ) {
                Text(
                    text = "Мои дела",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Выполнено — ${completedTasksCount}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ToggleIcon(onVisibilityChange = { newVisibility ->
                        isVisible = newVisibility})
                }

                LazyColumn {
                    items(items = visibleTasks) { task ->
                        TodoItem(task = task, onCheckedChange = { isChecked ->

                            tasks = tasks.map {
                                if (it.id == task.id) {
                                    it.copy(isCompleted = isChecked)
                                } else {
                                    it
                                }
                            }
                            completedTasksCount = tasks.count { it.isCompleted }
                        }, onDelete = {

                            tasks = tasks.filter { it.id != task.id }
                            completedTasksCount = tasks.count { it.isCompleted }
                        })
                    }
                }
            }
        }
    )
}

@Composable
fun CustomCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isImportant: Importance
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .padding(2.dp)
            .background(
                color = if (isChecked) Color.Green else if (isImportant.ordinal != 1) colorResource(
                    R.color.Color_gray_light
                ) else colorResource(R.color.white),
                shape = MaterialTheme.shapes.extraSmall
            )
            .border(
                width = 2.dp,
                color = if (isChecked) Color.Green else if (isImportant.ordinal != 1) Color.Red else Color.Gray,
                shape = MaterialTheme.shapes.extraSmall
            )
            .clickable { onCheckedChange(!isChecked) },
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
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoItem(task: ItemData, onCheckedChange: (Boolean) -> Unit, onDelete: () -> Unit) {
    val dismissState = rememberDismissState(DismissValue.Default)

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
                    isChecked = task.isCompleted,
                    onCheckedChange = onCheckedChange,
                    isImportant = task.importance
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = task.text,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
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
        dismissThresholds = { direction -> FractionalThreshold(0.5f) }, // 50% swipe qilinganda itemni o'chirish
        directions = setOf(DismissDirection.EndToStart)
    )

    if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
        Log.d("DeleteTask", "Dismissed: Starting delete task")
        TodoItemsRepository.deleteTask(task.id)
        onDelete()
        Log.d("DeleteTask", "Dismissed: Task deleted")
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(onTaskAdded: () -> Unit, onClose: () -> Unit) {

    var isImportant by remember { mutableStateOf(false) }
    var taskText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.back_primary))
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onClose) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        Text(
                            text = "СОХРАНИТЬ",
                            color = Color.Blue,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(R.color.back_primary)
                    )
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.back_primary))
            ) {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(colorResource(R.color.back_primary))
                ) {
                    TextField(
                        value = taskText,
                        onValueChange = { taskText = it },
                        placeholder = { Text("Что надо сделать...") },
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .border(1.dp, Color.Green, MaterialTheme.shapes.medium),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(R.color.back_secondary),
                            unfocusedIndicatorColor = Color.Transparent, // Chiziqni olib tashlaydi
                            focusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    ImportanceSelection()

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    )
                    DatePickerSwitch()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.5f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Удалить", color = Color.Gray)
                    }
                }
            }
        }

    }


}

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


@Composable
fun DatePickerSwitch() {
    var isSwitchOn by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Сделать до", modifier = Modifier.weight(1f))
            Switch(
                checked = isSwitchOn,
                onCheckedChange = { isChecked ->
                    isSwitchOn = isChecked
                    if (!isChecked) {
                        selectedDate = ""
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (isSwitchOn && selectedDate.isEmpty()) {
            var monthName = ""
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            android.app.DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    when (selectedMonth) {
                        0 -> monthName = "Января"
                        1 -> monthName = "Февраля"
                        2 -> monthName = "Марта"
                        3 -> monthName = "Апреля"
                        4 -> monthName = "Мая"
                        5 -> monthName = "Июня"
                        6 -> monthName = "Июля"
                        7 -> monthName = "Августа"
                        8 -> monthName = "Сентября"
                        9 -> monthName = "Октября"
                        10 -> monthName = "Ноября"
                        11 -> monthName = "Декабря"
                        else -> monthName = "Января"
                    }
                    selectedDate = "$selectedDay ${monthName} $selectedYear"
                },
                year, month, day
            ).show()
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedDate.isNotEmpty()) {
            Text(
                text = selectedDate,
                style = TextStyle(
                    color = colorResource(id = R.color.Color_blue),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            )


        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoComposeTheme {
        AddTaskScreen(onTaskAdded = {}, onClose = {})
    }
}