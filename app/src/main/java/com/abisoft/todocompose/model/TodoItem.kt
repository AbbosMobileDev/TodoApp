package com.abisoft.todocompose.model

import java.util.Date

data class ItemData (

    val id: String,
    var text: String,
    var importance: Importance,
    var deadline: Date? = null,
    var isCompleted: Boolean,
    var createdAt: Date,
    var modifiedAt: Date? = null

)
enum class Importance {
    LOW, NORMAL, URGENT
}


