import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.abisoft.todocompose.R
import com.abisoft.todocompose.TodoItemsRepository
import com.abisoft.todocompose.utils.TodoItem
import com.abisoft.todocompose.utils.ToggleIcon
import com.example.myapplication.okhttp_client.ApiClient
import com.example.myapplication.okhttp_client.TodoApiService

@Composable
fun TodoScreen(navController: NavController,
) {
    val apiService = ApiClient.create() // TodoApiService instansiyasini yaratish

// TodoItemsRepository ni yaratish
    val repository = TodoItemsRepository(apiService)
    var completedTasksCount by remember { mutableIntStateOf(0) }
    var isVisible by remember { mutableStateOf(true) }
    val viewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(repository) // Factory orqali ViewModelni yaratish
    )
    val todoState by viewModel.todoItems.collectAsState()

    LaunchedEffect(todoState) {
        completedTasksCount = todoState.count {
            it.done ==true}

    }
    val visibleTasks = if (isVisible) todoState else todoState.filter { it.done !=true}

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
                        text = "Выполнено — $completedTasksCount",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ToggleIcon(onVisibilityChange = { newVisibility ->
                        isVisible = newVisibility})
                }

                LazyColumn {
                    items(items = visibleTasks) { task ->
                        task.let {

                        TodoItem(
                            task = it,
                            onCheckedChange = { isChecked ->
                                viewModel.updateTaskCompletion(it.id.toString(), isChecked==true,0)
                            },
                            onDelete = {
                                viewModel.deleteTask(it.id.toString())
                            }
                        )
                        }
                    }
                }
            }
        }
    )
}
