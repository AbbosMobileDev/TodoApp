package com.abisoft.todocompose.api

import com.example.myapplication.okhttp_client.TodoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRequestData(
    @SerialName("element")
    val element: TodoDto

)
