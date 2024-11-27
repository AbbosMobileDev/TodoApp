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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.abisoft.mainscreenmodule.TodoViewModel
import com.abisoft.mainscreenmodule.TodoViewModelFactory
import com.abisoft.todocompose.R
import com.abisoft.todocompose.TodoItemsRepository
import com.abisoft.todocompose.datasource.TodoItemsDataSource
import com.abisoft.todocompose.utils.TodoItem
import com.abisoft.todocompose.utils.ToggleIcon
import com.example.myapplication.okhttp_client.ApiClient
@Composable
fun TodoScreen(navController: NavController) {
    val apiService = TodoItemsDataSource(ApiClient.create()) // TodoApiService instansiyasini yaratish
    val repository = TodoItemsRepository(apiService)
    val viewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory(repository))

    LaunchedEffect(Unit) {
        viewModel.getTasksList()
    }

    val todoList by viewModel.todoList.collectAsState()

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
                        text = "Выполнено — ${todoList.count { it.done }}", // Completed tasks count
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ToggleIcon(onVisibilityChange = { newVisibility ->
                    })
                }

                LazyColumn {
                    items(items = todoList) { task ->
                        TodoItem(
                            task = task,
                            onCheckedChange = {
                                viewModel.run {
                                    getTasksList()

                                }
                            },
                            onDelete = {
                            }
                        )
                    }
                }
            }
        }
    )
}

