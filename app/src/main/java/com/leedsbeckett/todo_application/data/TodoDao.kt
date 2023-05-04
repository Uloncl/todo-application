package com.leedsbeckett.todo_application.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Todo Data Access Object for accessing todos in the todo table
 */
@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(todo : Todo)

    @Delete
    suspend fun delete(todo : Todo)

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodoById(id : Int) : LiveData<Todo?>

    @Query("SELECT * FROM todo")
    fun getTodos() : LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE deleted IS NULL")
    fun getActiveTodos() : LiveData<List<Todo>>
}