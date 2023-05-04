package com.leedsbeckett.todo_application.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * entity class for defining the structure of the Todo table
 */
@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val title : String,
    val body : String?,
    val completed : Boolean = false,
    val created : Long,
    val updated : Long?,
    val timeout : Long?,
    val deleted : Long?
) {
}
