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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abisoft.todocompose.R
import com.abisoft.todocompose.TodoItemsRepository
import com.abisoft.todocompose.datasource.TodoItemsDataSource
import com.abisoft.todocompose.ui.viewModel.addTaskViewModel.AddTaskViewModel
import com.abisoft.todocompose.ui.viewModel.addTaskViewModel.AddViewModelFactory
import com.abisoft.todocompose.utils.DatePickerSwitch
import com.abisoft.todocompose.utils.ImportanceSelection
import com.example.myapplication.okhttp_client.ApiClient
import com.example.myapplication.okhttp_client.TodoDto
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.util.UUID
import javax.sql.DataSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(onTaskAdded: () -> Unit, onClose: () -> Unit) {
    val apiService = TodoItemsDataSource(ApiClient.create()) // TodoApiService instansiyasini yaratish
    val repository = TodoItemsRepository(apiService)
    val viewModel: AddTaskViewModel = viewModel(factory = AddViewModelFactory(repository))

    val isImportant by remember { mutableStateOf(false) }
    var taskText by remember { mutableStateOf("") }
    val taskDeadline by remember { mutableStateOf(0) } // Deadline uchun o'zgaruvchini qo'shish
    val taskColor by remember { mutableStateOf("") } // Rang uchun o'zgaruvchini qo'shish

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
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    val newTodo = TodoDto(
                                        id = UUID.randomUUID().toString(), // Generate a UUID for the new task
                                        text = taskText,
                                        importance = if (isImportant) "High" else "Normal", // Set importance
                                        deadline = taskDeadline,
                                        done = false,
                                        color = taskColor,
                                        createdAt = System.currentTimeMillis().toInt(),
                                        changedAt = System.currentTimeMillis().toInt(),
                                        lastUpdatedBy = "User",
                                        )
                                   viewModel.viewModelScope.launch {
                                       viewModel.addNewTask(newTodo)
                                       onTaskAdded()

                                   }

                                    onClose()
                                }
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
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ImportanceSelection() // Importansni tanlash

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    )
                    DatePickerSwitch() // Sana tanlash

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
