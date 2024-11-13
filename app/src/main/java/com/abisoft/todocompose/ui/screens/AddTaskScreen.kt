import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.abisoft.todocompose.R
import com.abisoft.todocompose.utils.DatePickerSwitch
import com.abisoft.todocompose.utils.ImportanceSelection

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
//

}