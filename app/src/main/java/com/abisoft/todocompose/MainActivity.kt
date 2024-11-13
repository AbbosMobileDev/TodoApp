package com.abisoft.todocompose

import AddTaskScreen
import TodoScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoComposeTheme {
        AddTaskScreen(onTaskAdded = {}, onClose = {})
    }
}